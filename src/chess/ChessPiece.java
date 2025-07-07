package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {
	private Color color;
	private int moveCount;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(pos);
	}
	
	public int getMoveCount() {
		return moveCount;
	}
	
	public void increaseMove() {
		moveCount++;
	}
	
	public void decreaseMove() {
		moveCount--;
	}
	
	protected boolean enemyAt(Position pos) {
		ChessPiece p = (ChessPiece) getBoard().piece(pos);
		return p != null && p.getColor() != this.color;
	}
}