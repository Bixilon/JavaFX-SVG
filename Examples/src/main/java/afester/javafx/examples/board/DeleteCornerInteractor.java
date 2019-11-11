package afester.javafx.examples.board;

import java.util.List;

import afester.javafx.examples.board.view.BoardHandle;
import afester.javafx.examples.board.view.BoardView;
import javafx.geometry.Point2D;

public class DeleteCornerInteractor extends MouseInteractor {

    public DeleteCornerInteractor(BoardView boardView) {
        super(boardView);
    }


    @Override
    protected List<Interactable> pickObjects(Point2D mpos) {
        return getBoardView().getBoardHandleGroup().pickAll(mpos);
    }

    
    @Override
    protected void clickObject(Interactable obj) {
        System.err.println("Clicked " + obj);
        BoardHandle bh = (BoardHandle) obj;
        getBoardView().getBoard().deleteCorner(bh.getCornerIdx());
    }

    @Override
    public String toString() {
        return "DeleteCornerInteractor";
    }
}
