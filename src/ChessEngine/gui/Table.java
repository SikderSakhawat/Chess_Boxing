
package ChessEngine.gui;

import ChessEngine.board.BoardUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class Table {
    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private final Color lightTileColor = Color.decode("#FFFACD");
    private final Color darkTileColor = Color.decode("#593E1A");
    private final static Dimension BOARD_PANEL_SIZE = new Dimension(400, 350);
    private final static Dimension FRAME_DIMENSIONS = new Dimension(800,800);
    private final static Dimension TILE_DIMENSION = new Dimension(10,10);
    public Table() {
        this.gameFrame = new JFrame("Chess Boxing!");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(FRAME_DIMENSIONS);
        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.setVisible(true);
    }

    private JMenuBar createTableMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
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
    }

    private class TilePanel extends JPanel{
        private final int tileID;
        public TilePanel(final BoardPanel boardPanel, final int tileID){
            super(new GridBagLayout());
            this.tileID = tileID;
            setPreferredSize(TILE_DIMENSION);
            assignTileColor();
            validate();
        }

        private void assignTileColor() {
            if(BoardUtility.EIGHTH_RANK[this.tileID] || BoardUtility.SIXTH_RANK[this.tileID] || BoardUtility.FOURTH_RANK[this.tileID] || BoardUtility.SECOND_RANK[this.tileID]) {
                setBackground(this.tileID % 2 == 0 ? lightTileColor : darkTileColor);
            } else if (BoardUtility.SEVENTH_RANK[this.tileID] || BoardUtility.FIFTH_RANK[this.tileID] || BoardUtility.THIRD_RANK[this.tileID] || BoardUtility.FIRST_RANK[this.tileID]) {
                setBackground(this.tileID % 2 != 0 ? lightTileColor : darkTileColor);
            }
        }
    }
}