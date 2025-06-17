package boardgame;

public class Piece {
	protected Position pos;
	private Board board;
	
	public Piece(Board board) {
		this.pos = null;
		this.board = board;
	}

	protected Board getBoard() {
		return board;
	}
	
	
}