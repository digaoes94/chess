package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {
	private ChessMatch match;

	public Pawn(Board board, Color color, ChessMatch match) {
		super(board, color);
		this.match = match;
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getCols()];
		Position p = new Position(0, 0);
		
		//white pawn
		if(this.getColor() == Color.WHITE) {
			//normal move
			p.setValues(pos.getRow() - 1, pos.getCol());
			if(getBoard().positionExists(p) && !getBoard().positionOccupied(p)) {
				mat[p.getRow()][p.getCol()] = true;
			}
			
			//first move
			p.setValues(pos.getRow() - 2, pos.getCol());
			Position p2 = new Position(pos.getRow() - 1, pos.getCol());
			if(getBoard().positionExists(p) && !getBoard().positionOccupied(p) && 
			   getBoard().positionExists(p2) && !getBoard().positionOccupied(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getCol()] = true;
			}
			
			//eat left
			p.setValues(pos.getRow() - 1, pos.getCol() - 1);
			if(getBoard().positionExists(p) && enemyAt(p)) {
				mat[p.getRow()][p.getCol()] = true;
			}

			//eat right
			p.setValues(pos.getRow() - 1, pos.getCol() + 1);
			if(getBoard().positionExists(p) && enemyAt(p)) {
				mat[p.getRow()][p.getCol()] = true;
			}
			
			//en passant white
			if(pos.getRow() == 3) {
				Position left = new Position(pos.getRow(), pos.getCol() - 1);
				if(getBoard().positionExists(left) && enemyAt(left) && getBoard().piece(left) == match.getVPassant()) {
					mat[left.getRow() - 1][left.getCol()] = true;
				}
				
				Position right = new Position(pos.getRow(), pos.getCol() + 1);
				if(getBoard().positionExists(right) && enemyAt(right) && getBoard().piece(right) == match.getVPassant()) {
					mat[left.getRow() - 1][left.getCol()] = true;
				}
			}
		}
		else {
			//normal move
			p.setValues(pos.getRow() + 1, pos.getCol());
			if(getBoard().positionExists(p) && !getBoard().positionOccupied(p)) {
				mat[p.getRow()][p.getCol()] = true;
			}
			
			//first move
			p.setValues(pos.getRow() + 2, pos.getCol());
			Position p2 = new Position(pos.getRow() + 1, pos.getCol());
			if(getBoard().positionExists(p) && !getBoard().positionOccupied(p) && 
			   getBoard().positionExists(p2) && !getBoard().positionOccupied(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getCol()] = true;
			}
			
			//eat right
			p.setValues(pos.getRow() + 1, pos.getCol() - 1);
			if(getBoard().positionExists(p) && enemyAt(p)) {
				mat[p.getRow()][p.getCol()] = true;
			}

			//eat left
			p.setValues(pos.getRow() + 1, pos.getCol() + 1);
			if(getBoard().positionExists(p) && enemyAt(p)) {
				mat[p.getRow()][p.getCol()] = true;
			}
			
			//en passant black
			if(pos.getRow() == 4) {
				Position left = new Position(pos.getRow(), pos.getCol() - 1);
				if(getBoard().positionExists(left) && enemyAt(left) && getBoard().piece(left) == match.getVPassant()) {
					mat[left.getRow() + 1][left.getCol()] = true;
				}
				
				Position right = new Position(pos.getRow(), pos.getCol() + 1);
				if(getBoard().positionExists(right) && enemyAt(right) && getBoard().piece(right) == match.getVPassant()) {
					mat[left.getRow() + 1][left.getCol()] = true;
				}
			}
		}
		
		return mat;
	}

	@Override
	public String toString() {
		return "P";
	}
	
	
}