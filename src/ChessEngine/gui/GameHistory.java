package ChessEngine.gui;

import ChessEngine.board.Board;
import ChessEngine.player.Move;

import static ChessEngine.gui.Table.MoveLog;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameHistory extends JPanel {

    private final DataModel model;
    private final JScrollPane scrollPane;
    private static final Dimension HISTORY_PANEL_DIMENSION = new Dimension(100,400);
    GameHistory(){
        this.setLayout(new BorderLayout());
        this.model = new DataModel();
        final JTable table = new JTable(model);
        table.setRowHeight(15);
        this.scrollPane = new JScrollPane();
        scrollPane.setColumnHeaderView(table.getTableHeader());
        scrollPane.setPreferredSize(HISTORY_PANEL_DIMENSION);
        this.add(scrollPane, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public void redo(final Board board, final MoveLog pastMoves){
        int currentRow = 0;
        this.model.clear();
        for(final Move move : pastMoves.getMoves()){
            final String moveText = move.toString();
            if(move.getMovePiece().getPiecedAlliance().isWhite()){
                this.model.setValueAt(moveText, currentRow, 0);
            } else if (move.getMovePiece().getPiecedAlliance().isBlack()){
                this.model.setValueAt(moveText, currentRow, 1);
                currentRow++;
            }
        }
        if(pastMoves.getMoves().size() > 0){
            final Move lastMove  = pastMoves.getMoves().get(pastMoves.size()-1);
            final String moveText = lastMove.toString();
            if (lastMove.getMovePiece().getPiecedAlliance().isWhite()) {
                this.model.setValueAt(moveText + calculateCheckOrMateHash(board), currentRow - 1, 0 );
            } else if(lastMove.getMovePiece().getPiecedAlliance().isBlack()){
                this.model.setValueAt(moveText + calculateCheckOrMateHash(board), currentRow - 1, 1 );
            }
        }
        final JScrollBar vertical = scrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    private String calculateCheckOrMateHash(final Board board) {
        if(board.currentPlayer().isInCheckMate()) return "#";
        else if(board.currentPlayer().isInCheck()) return "+";
        return "";
    }

    private static class DataModel extends DefaultTableModel {
        private final List<Row> values;
        private static final String[] NAMES = {"White", "Black"};
        DataModel(){
            this.values = new ArrayList<>();
        }

        public void clear(){
            values.clear();
            setRowCount(0);
        }

        public int getRowCount(){
            if(this.values == null) return 0;
            return this.values.size();
        }

        public int getColCount(){
            return NAMES.length;
        }

        public Object getValueAt(final int row, final int col){
            final Row currentRow = this.values.get(row);
            if(col == 0) return currentRow.getWhiteMove();
            else if (col == 1) return currentRow.getBlackMove();
            return null;
        }

        public void setValueAt(final Object aVal, final int row, final int col){
            final Row currentRow;
            if(this.values.size() <= row){
                currentRow = new Row();
                this.values.add(currentRow);
            } else {
                currentRow = this.values.get(row);
            }
            if(col == 0){
                currentRow.setWhiteMove((String) aVal);
                fireTableRowsInserted(row, row);
            }
            else if(col == 1){
                currentRow.setBlackMove((String) aVal);
                fireTableCellUpdated(row, col);
            }
        }

        public Class<?> getColumnClass(final int col){
            return Move.class;
        }

        public String getColName(final int col){
            return NAMES[col];
        }
    }

    private static class Row {
        private static String whiteMove;
        private static String blackMove;

        public String getWhiteMove(){return this.whiteMove;}
        public String getBlackMove(){return this.blackMove;}
        public  void setBlackMove(String move) {this.blackMove = move;}
        public void setWhiteMove(String move) {this.whiteMove = move;}
    }
}