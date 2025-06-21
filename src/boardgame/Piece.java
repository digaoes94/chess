package boardgame;

public abstract class Piece {
	protected Position pos;
	private Board board;
	
	public Piece(Board board) {
		this.pos = null;
		this.board = board;
	}

	protected Board getBoard() {
		return board;
	}
	
	public abstract boolean[][] possibleMoves();
	
	public boolean possibleMove(Position pos) {
		return possibleMoves()[pos.getRow()][pos.getCol()];
	}
	
	public boolean possiblePath() {
		boolean[][] mat = possibleMoves();
		
		for (int a = 0; a < mat.length; a++) {
			for (int b = 0; b < mat.length; b++) {
				if (mat[a][b]) {
					return true;
				}
			}
		}
		
		return false;
	}
}