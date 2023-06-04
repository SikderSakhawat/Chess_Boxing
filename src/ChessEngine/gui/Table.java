package ChessEngine.gui;

import ChessEngine.board.Board;
import ChessEngine.board.BoardUtility;
import ChessEngine.board.Tile;
import ChessEngine.piece.Piece;
import ChessEngine.player.Move;
import ChessEngine.player.MoveTransition;
import com.google.common.collect.Lists;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Table {
    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    public Board chessBoard;

    private Tile sourceTile;
    private Tile destinationTile;
    private Piece manMovedPiece;
    private BoardDirection boardDirection;
    private boolean highlightLegalMoves;
    private final Color lightTileColor = Color.decode("#eeeed2");
    private final Color darkTileColor = Color.decode("#769656");
    private final static Dimension BOARD_PANEL_SIZE = new Dimension(400, 350);
    private final static Dimension FRAME_DIMENSIONS = new Dimension(800,800);
    private final static Dimension TILE_DIMENSION = new Dimension(10,10);
    private static String defaultPieceImgPath = "ChessBoxing/ChessPieceFiles/";
    public Table() {
        this.gameFrame = new JFrame("Chess Boxing!");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.chessBoard = Board.createStandardBoard();
        this.gameFrame.setSize(FRAME_DIMENSIONS);
        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.setVisible(true);
        this.boardDirection = BoardDirection.NORMAL;
        this.highlightLegalMoves = false;
    }

    private JMenuBar createTableMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        tableMenuBar.add(createPreferencesMenu());
        return tableMenuBar;
    }

    private JMenu createFileMenu(){
        final JMenu fileMenu = new JMenu("File");

        final JMenuItem openPGN = new JMenuItem("Load PGN File");
        openPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("open up that PGN file!!");
            }
        });
        fileMenu.add(openPGN);
        final JMenuItem exitJMenuItem = new JMenuItem("Exit");
        exitJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitJMenuItem);
        return fileMenu;
    }

    // will allow you to have the choice of flipping the board (if its black's turn for example)
    private JMenu createPreferencesMenu(){
        final JMenu preferenceMenu = new JMenu("Preferences");
        final JMenuItem flipBoardItem = new JMenuItem("Flip Board");
        flipBoardItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boardDirection = boardDirection.opposite();
                boardPanel.drawBoard(chessBoard);
            }
        });
        preferenceMenu.add(flipBoardItem);
        preferenceMenu.addSeparator();
        final JCheckBoxMenuItem legalMoveHighLighterCheckbox = new JCheckBoxMenuItem("Highlight legal Moves", false);
        legalMoveHighLighterCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                highlightLegalMoves = legalMoveHighLighterCheckbox.isSelected();
            }
        });
        preferenceMenu.add(legalMoveHighLighterCheckbox);
        return preferenceMenu;
    }

    private class BoardPanel extends JPanel{
        final List<TilePanel> boardTiles;

        BoardPanel(){
            super(new GridLayout(8,8));
            this.boardTiles = new ArrayList<>();
            for(int i = 0; i < BoardUtility.NUM_TILES; i++){
                final TilePanel tilePanel = new TilePanel(this,i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_SIZE);
            validate();
        }

        public void drawBoard(Board board){
            removeAll();
            for(final TilePanel tilePanel : boardDirection.traverse(boardTiles)){
                tilePanel.drawTile(board);
                add(tilePanel);
            }
            validate();
            repaint();
        }
    }

    private class TilePanel extends JPanel{
        private final int tileID;
        public TilePanel(final BoardPanel boardPanel, final int tileID){
            super(new GridBagLayout());
            this.tileID = tileID;
            setPreferredSize(TILE_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(chessBoard);

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent e) {
                    // right click discards your move selection
                    if (isRightMouseButton(e)) {
                        sourceTile = null;
                        destinationTile = null;
                        manMovedPiece = null;
                    } else if (isLeftMouseButton(e)) {
                        // once you click on your left mouse, if there has not been a selected move,
                        // you can move a piece and the piece will be moved
                        if (sourceTile == null) {
                            sourceTile = chessBoard.getTile(tileID);
                            manMovedPiece = sourceTile.getPiece();
                            if (manMovedPiece == null) sourceTile = null;
                        } else {
                            destinationTile = chessBoard.getTile(tileID);
                            final Move move = Move.MoveFactory.createMove(chessBoard, sourceTile.getTileCoord(), destinationTile.getTileCoord());
                            final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
                            if(transition.getMoveStatus.isDone()) chessBoard = transition.getTransBoard();
                            // todo add move that was made to moveLog
                        }
                        // after move selection we are back to not selecting a move
                        sourceTile = null;
                        destinationTile = null;
                        manMovedPiece = null;
                    }
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            boardPanel.drawBoard(chessBoard);
                        }
                    });
                }
                @Override
                public void mousePressed(final MouseEvent e) {

                }

                @Override
                public void mouseReleased(final MouseEvent e) {

                }

                @Override
                public void mouseEntered(final MouseEvent e) {

                }

                @Override
                public void mouseExited(final MouseEvent e) {

                }
            });
            validate();
        }

        public void drawTile(final Board board){
            assignTileColor();
            assignTilePieceIcon(board);
            validate();
            repaint();
        }

        private void highlightMove(final Board board){
            if(highlightLegalMoves){
                for(final Move move : piecesLegalMoves(board)){
                    if(move.getDestinationCoord() == this.tileID){
                        try{
                            add(new JLabel(new ImageIcon(ImageIO.read(new File("Chess Boxing/ChessPieceFiles/green_dot.png")))));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        // checks all the current possible moves that a current piece contains for the current player move
        // if there is no piece selected there are no possible moves, if the piece has no moves it will return an empty list as well
        private Collection<Move> piecesLegalMoves(final Board board){
            if(manMovedPiece != null && manMovedPiece.getPiecedAlliance() == board.currentPlayer().getAlliance()){
                return manMovedPiece.calcLegalMoves(board);
            }
            return Collections.emptyList();
        }

        private void assignTilePieceIcon(final Board board){
            this.removeAll();
            // If there is a piece on a given tile, we will create an image of that piece based on the string letter of that piece
            // e.g. Knight is "N", King is "K", etc.
            // piece files will follow this convention (e.g. white bishop file is WB.gif)
            if(board.getTile(this.tileID).isOccupied()){
                try {
                    final BufferedImage image = ImageIO.read(new File(defaultPieceImgPath +
                            board.getTile(this.tileID).getPiece().getPiecedAlliance().toString().substring(0,1) +
                            board.getTile(tileID).getPiece().toString() + ".gif"));
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        private void assignTileColor() {
            if(BoardUtility.EIGHTH_RANK[this.tileID] || BoardUtility.SIXTH_RANK[this.tileID] || BoardUtility.FOURTH_RANK[this.tileID] || BoardUtility.SECOND_RANK[this.tileID]) {
                setBackground(this.tileID % 2 == 0 ? lightTileColor : darkTileColor);
            } else if (BoardUtility.SEVENTH_RANK[this.tileID] || BoardUtility.FIFTH_RANK[this.tileID] || BoardUtility.THIRD_RANK[this.tileID] || BoardUtility.FIRST_RANK[this.tileID]) {
                setBackground(this.tileID % 2 != 0 ? lightTileColor : darkTileColor);
            }
        }
    }

    // checks which way the board is moving.
    public enum BoardDirection{
        NORMAL{
            @Override
            public List<TilePanel> traverse(List<TilePanel> boardTiles) {
                return boardTiles;
            }

            @Override
            public BoardDirection opposite() {
                return FLIPPED;
            }
        },
        FLIPPED {
            @Override
            public List<TilePanel> traverse(List<TilePanel> boardTiles) {
                return Lists.reverse(boardTiles);
            }

            @Override
            public BoardDirection opposite() {
                return NORMAL;
            }
        };

        public abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);
        public abstract BoardDirection opposite();
    }
}