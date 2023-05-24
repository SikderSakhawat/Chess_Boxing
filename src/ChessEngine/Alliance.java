package ChessEngine;

public enum Alliance {
    // there is only two instance that we need to keep in check: white and black, these can be modified with their behavior
    WHITE{
        public int getDirection(){
            return -1;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public boolean isWhite() {
            return true;
        }

    },
    BLACK{
        public int getDirection(){
            return 1;
        }

        @Override
        public boolean isBlack() {
            return true;
        }

        @Override
        public boolean isWhite() {
            return false;
        }
    };

    public abstract int getDirection();
    public abstract boolean isBlack();
    public abstract boolean isWhite();
}
