package ChessEngine.gui;

import ChessEngine.piece.Piece;
import ChessEngine.player.Move;
import com.google.common.primitives.Ints;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TakenPiecePanel extends JPanel {
    private final JPanel topPanel;
    private final JPanel botPanel;

    private static final Color PANEL_COLOR = Color.decode("#000000");
    private static final Dimension TAKEN_PIECES_DIMENSION = new Dimension(40,80);
    private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);
    public TakenPiecePanel(){
        super(new BorderLayout());
        setBackground(PANEL_COLOR);
        setBorder(PANEL_BORDER);
        this.topPanel = new JPanel(new GridLayout(8,2));
        this.botPanel = new JPanel(new GridLayout(8,2));
        this.topPanel.setBackground(PANEL_COLOR);
        this.botPanel.setBackground(PANEL_COLOR);
        this.add(this.topPanel, BorderLayout.NORTH);
        this.add(this.botPanel, BorderLayout.SOUTH);
        setPreferredSize(TAKEN_PIECES_DIMENSION);
    }

    public void redo(final Table.MoveLog moveLog){
        this.botPanel.removeAll();
        this.topPanel.removeAll();

        final List<Piece> whitePieceTaken = new ArrayList<>();
        final List<Piece> blackPieceTaken = new ArrayList<>();
        for(final Move move : moveLog.getMoves()){
            if(move.isAttack()){
                final Piece takenPiece = move.getAttackedPiece();
                if(takenPiece.getPiecedAlliance().isWhite()){
                    whitePieceTaken.add(takenPiece);
                } else if(takenPiece.getPiecedAlliance().isBlack()){
                    blackPieceTaken.add(takenPiece);
                } else {
                    throw new RuntimeException("Should Not Reach Here!!");
                }
            }
        }
        Collections.sort(whitePieceTaken, new Comparator<Piece>() {
            @Override
            public int compare(Piece o1, Piece o2) {
                return Ints.compare(o1.getPieceValue(), o2.getPieceValue());
            }
        });
        Collections.sort(blackPieceTaken, new Comparator<Piece>() {
            @Override
            public int compare(Piece o1, Piece o2) {
                return Ints.compare(o1.getPieceValue(), o2.getPieceValue());
            }
        });

        for(final Piece takenPiece : whitePieceTaken){
            try{
                final BufferedImage image = ImageIO.read(new File("ChessBoxing/ChessPieceFiles" +
                        takenPiece.getPiecedAlliance().toString().substring(0,1) + "" + takenPiece.toString()));
                final ImageIcon  icon = new ImageIcon(image);
                final JLabel imgLabel = new JLabel();
                this.botPanel.add(imgLabel);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        validate();
    }
}