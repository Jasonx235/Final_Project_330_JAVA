import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ComputerBoard extends Parent implements Board{

    private VBox rows = new VBox();
    private final boolean isEnemy = true;
    public final int x = 10;
    public final int y = 10;
    public int addPiece = 0;
    public boolean cheatOnOff = false;

    public ComputerBoard(){
        for(int y = 0; y < this.y; y++){
            HBox row = new HBox();
            for(int x =0; x < this.x; x++){
                position p = new position(x, y, this);
                row.getChildren().add(p);
            }
            rows.getChildren().add(row);
        }
        getChildren().add(rows);
    }

    public void setCheatOnOff(boolean cheatOnOff) {
        this.cheatOnOff = cheatOnOff;
    }

    public position getPosition(int x, int y){
        return (position)((HBox)rows.getChildren().get(y)).getChildren().get(x);
    }

    public position isValidPosition(int x, int y) throws IndexOutOfBoundsException {
        return (position) ((HBox) rows.getChildren().get(y)).getChildren().get(x);
    }

    public boolean setPiece(int row, int col, String Dir, GamePieces piece) throws IndexOutOfBoundsException{

        int size = piece.getSize();
        int mode = 0;

        int pieceName = 0;

        if(cheatOnOff == true) {
            if (piece.getName() == "Carrot") {
                pieceName = 3;
            } else if (piece.getName() == "Potato") {
                pieceName = 4;
            } else if (piece.getName() == "Tomato") {
                pieceName = 5;
            } else if (piece.getName() == "Pea") {
                pieceName = 6;
            }
        }

        try {
            this.isValidPosition(row+size, col+size);
        } catch (IndexOutOfBoundsException e){
            if(row >= 10 || col >= 10 || row < 0 || col < 0) {
                throw new IndexOutOfBoundsException("Invalid Location");
            }
            else {
                mode = 1;
            }
        }

        if(mode == 0) {
            if (Dir == "VERTICAL") {
                for (int i = 0; i < size; i++) {
                    if (!this.positionValid(row, col + i)) {
                        return false;
                    }
                }
                for (int i = 0; i < size; i++) {
                    if(cheatOnOff) {
                        this.getPosition(row, col + i).changeColor(row, col + i, pieceName);
                    }
                    this.getPosition(row, col + i).setOccupied(true);
                    this.addPiece++;
                }
            } else if (Dir == "HORIZONTAL") {
                for (int i = 0; i < size; i++) {
                    if (!this.positionValid(row + i, col)) {
                        return false;
                    }
                }
                for (int i = 0; i < size; i++) {
                    if(cheatOnOff) {
                        this.getPosition(row + i, col).changeColor(row + i, col, pieceName);
                    }
                    this.getPosition(row + i, col).setOccupied(true);
                    this.addPiece++;
                }

            }
        }
        else if(mode == 1) {

            if (Dir == "VERTICAL") {
                for (int i = size-1; i >= 0; i--) {
                    if (!this.positionValid(row, col - i)) {
                        return false;
                    }
                }
                for (int i = size-1; i >= 0; i--) {
                    if(cheatOnOff) {
                        this.getPosition(row, col - i).changeColor(row, col - i, pieceName);
                    }
                    this.getPosition(row, col - i).setOccupied(true);
                    this.addPiece++;
                }
            } else if (Dir == "HORIZONTAL") {
                for (int i = size-1; i >= 0; i--) {
                    if (!this.positionValid(row - i, col)) {
                        return false;
                    }
                }
                for (int i = size-1; i >= 0; i--) {
                    if(cheatOnOff) {
                        this.getPosition(row - i, col).changeColor(row - i, col, pieceName);
                    }
                    this.getPosition(row - i, col).setOccupied(true);
                    this.addPiece++;
                }

            }

        }


        return true;
    }

    public boolean positionValid(int row, int col){
        if(this.getPosition(row, col).getOccupied()){
            return false;
        }
        else if((row >= 10 || col >= 10) && (row < 0 || col < 0)) {
            throw new IllegalArgumentException("Invalid Row and Column!");
        }
        else {
            return true;
        }
    }

    @Override
    public int attack(int row, int col, Board other) throws IndexOutOfBoundsException {
        try {
            other.positionValid(row, col);
        } catch (IndexOutOfBoundsException e){
            if(row >= 10 || col >= 10 || row < 0 || col < 0) {
                throw new IndexOutOfBoundsException("Invalid Location");
            }
        }
        if(other.getPosition(row, col).getOccupied() && !other.getPosition(row, col).isGuess()) {
            other.getPosition(row, col).setGuess(true);
            other.getPosition(row, col).setHitOrMiss(true);
            other.getPosition(row, col).changeColor(row, col, 0);
            other.setAddPiece(other.getAddPiece()-1);
            return 1;
        }
        else if(other.getPosition(row, col).isGuess()){
            return 2;

        }
        else if(!other.getPosition(row, col).getOccupied()) {
            other.getPosition(row, col).setGuess(true);
            other.getPosition(row, col).setHitOrMiss(false);
            other.getPosition(row, col).changeColor(row, col, 1);
            return 3;
        }

        return 0;

    }

    public int getAddPiece() {
        return addPiece;
    }

    public void setAddPiece(int addPiece) {
        this.addPiece = addPiece;
    }

    @Override
    public boolean getIsEnemy() {
        return isEnemy;
    }
}

