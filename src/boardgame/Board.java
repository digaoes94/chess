package boardgame;

import boardgame.exceptions.BoardException;

public class Board {
	private int rows;
	private int cols;
	private Piece[][] pieces;
	
	public Board(int rows, int cols) {
		if (rows < 1 || cols < 1) {
			throw new BoardException("Chess boards are an 8x8 matrix.");
		}
		this.rows = rows;
		this.cols = cols;
		this.pieces = new Piece[rows][cols];
	}

	public int getRows() {
		return rows;
	}
	
	public int getCols() {
		return cols;
	}
	
	public Piece piece(int row, int col) {
		if (!positionExists(row, col)) {
			throw new BoardException("Choose a valid position on the board.");
		}
		return pieces[row][col];
	}
	public Piece piece(Position pos) {
		if (!positionExists(pos)) {
			throw new BoardException("Choose a valid position on the board.");
		}
		return pieces[pos.getRow()][pos.getCol()];
	}
	
	public void placePiece(Piece piece, Position pos) {
		if (positionOccupied(pos)) {
			throw new BoardException("You can not eat your own pieces.");
		}
		pieces[pos.getRow()][pos.getCol()] = piece;
		piece.pos = pos;
	}
	
	public boolean positionExists(int row, int col) {
		return row >= 0 && row < rows && col >= 0 && col < cols;
	}
	public boolean positionExists(Position pos) {
		return positionExists(pos.getRow(), pos.getCol());
	}
	
	public boolean positionOccupied(Position pos) {
		if (!positionExists(pos)) {
			throw new BoardException("Choose a valid position on the board.");
		}
		return piece(pos) != null;
	}
}
