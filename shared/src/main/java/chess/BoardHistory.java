package chess;

import java.util.ArrayList;
import java.util.List;

public record BoardHistory() {
    static List<ChessPiece[][]> archive = new ArrayList<>();
    private static int archiveSize = 0;

    public void addBoard(ChessPiece[][] board) {
        archive.add(board);
        archiveSize++;
    }

    public ChessPiece[][] getBoard(int index) {
        return archive.get(index);
    }

    public int getArchiveSize() {
        return archiveSize;
    }
}
