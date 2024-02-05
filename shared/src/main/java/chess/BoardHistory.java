package chess;

import java.util.ArrayList;
import java.util.List;

public record BoardHistory() {
    static List<ChessBoard> archive = new ArrayList<>();

    public void addBoard(ChessBoard board) {
        archive.add(board);
    }

    public ChessBoard getBoard(int index) {
        return archive.get(index);
    }
}
