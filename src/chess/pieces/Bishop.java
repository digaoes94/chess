package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {

	public Bishop(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getCols()];
		Position p = new Position(0, 0);
		
		// up-right
		p.setValues(pos.getRow() - 1, pos.getCol() + 1);
		while (getBoard().positionExists(p) && !getBoard().positionOccupied(p)) {
			mat[p.getRow()][p.getCol()] = true;
			p.setValues(p.getRow() - 1, p.getCol() + 1);
		}
		if (getBoard().positionExists(p) && enemyAt(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}
		
		// down-right
		p.setValues(pos.getRow() + 1, pos.getCol() + 1);
		while (getBoard().positionExists(p) && !getBoard().positionOccupied(p)) {
			mat[p.getRow()][p.getCol()] = true;
			p.setValues(p.getRow() + 1, p.getCol() + 1);
		}
		if (getBoard().positionExists(p) && enemyAt(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}
		
		// down-left
		p.setValues(pos.getRow() + 1, pos.getCol() - 1);
		while (getBoard().positionExists(p) && !getBoard().positionOccupied(p)) {
			mat[p.getRow()][p.getCol()] = true;
			p.setValues(p.getRow() + 1, p.getCol() - 1);
		}
		if (getBoard().positionExists(p) && enemyAt(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}
		
		// up-left
		p.setValues(pos.getRow() - 1, pos.getCol() - 1);
		while (getBoard().positionExists(p) && !getBoard().positionOccupied(p)) {
			mat[p.getRow()][p.getCol()] = true;
			p.setValues(p.getRow() - 1, p.getCol() - 1);
		}
		if (getBoard().positionExists(p) && enemyAt(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}
		
		return mat;
	}

}