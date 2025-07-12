package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.exceptions.ChessException;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {
	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;
	
	private List<Piece> piecesAvaible = new ArrayList<Piece>();
	private List<Piece> piecesCaptures = new ArrayList<Piece>();
	
	public ChessMatch() {
		this.board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}
	
	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getCols()];
		
		for (int a = 0; a < board.getRows(); a++) {
			for(int b = 0; b < board.getCols(); b++) {
				mat[a][b] = (ChessPiece) board.piece(a, b);
			}
		}
		
		return mat;
	}
	
	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
	}

	public boolean[][] possibleMoves(ChessPosition before) {
		Position pos = before.toPosition();
		validateBefore(pos);
		return board.piece(pos).possibleMoves();
	}
	
	public ChessPiece movePiece(ChessPosition before, ChessPosition after) {
		Position bef = before.toPosition();
		Position af = after.toPosition();
		validateBefore(bef);
		validateAfter(bef, af);
		Piece captured = makeMove(bef, af);
		
		if(testCheck(currentPlayer)) {
			undoCheck(bef, af, captured);
			throw new ChessException("You stupid? Don't put yourself in CHECK! C'mon dude.");
		}
		check = (testCheck(enemy(currentPlayer))) ? true : false;
		
		if(testCheckMate(enemy(currentPlayer))) {
			checkMate = true;
		}
		else {
			nextTurn();
		}
		
		return (ChessPiece) captured;
	}
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private void validateBefore(Position pos) {
		if (!board.positionOccupied(pos)) {
			throw new ChessException("Original position is not occupied.");
		}
		if (currentPlayer != ((ChessPiece) board.piece(pos)).getColor()) {
			throw new ChessException("You can only move your own pieces. Choose wisely.");
		}
		if (!board.piece(pos).possiblePath()) {
			throw new ChessException("No possible moves for this piece.");
		}
	}
	
	private void validateAfter(Position before, Position after) {
		if (!board.piece(before).possibleMove(after)) {
			throw new ChessException("Invalid move. Choose a valid move for this piece.");
		}
	}
	
	private Piece makeMove(Position before, Position after) {
		ChessPiece p = (ChessPiece) board.removePiece(before);
		p.increaseMove();
		Piece captured = board.removePiece(after);
		board.placePiece(p, after);
		if (captured != null) {
			piecesAvaible.remove(captured);
			piecesCaptures.add(captured);
		}
		
		//castlings
		//close castling
		if(p instanceof King && after.getCol() == before.getCol() + 2) {
			Position beforeRook = new Position(before.getRow(), before.getCol() + 3);
			Position afterRook = new Position(before.getRow(), before.getCol() + 1);
			ChessPiece closeRook = (ChessPiece) board.removePiece(beforeRook);
			board.placePiece(closeRook, afterRook);
			closeRook.increaseMove();
		}
		//far castling
		if(p instanceof King && after.getCol() == before.getCol() - 2) {
			Position beforeRook = new Position(before.getRow(), before.getCol() - 4);
			Position afterRook = new Position(before.getRow(), before.getCol() - 1);
			ChessPiece closeRook = (ChessPiece) board.removePiece(beforeRook);
			board.placePiece(closeRook, afterRook);
			closeRook.increaseMove();
		}
		
		return captured;
	}
	
	private void undoCheck(Position before, Position after, Piece captured) {
		ChessPiece p = (ChessPiece) board.removePiece(after);
		p.decreaseMove();
		board.placePiece(p, before);
		
		if (captured != null) {
			board.placePiece(p, after);
			piecesCaptures.remove(captured);
			piecesAvaible.add(captured);
		}
		
		//undo castlings
		//close castling
		if(p instanceof King && after.getCol() == before.getCol() + 2) {
			Position beforeRook = new Position(before.getRow(), before.getCol() + 3);
			Position afterRook = new Position(before.getRow(), before.getCol() + 1);
			ChessPiece closeRook = (ChessPiece) board.removePiece(afterRook);
			board.placePiece(closeRook, beforeRook);
			closeRook.decreaseMove();
		}
		//far castling
		if(p instanceof King && after.getCol() == before.getCol() - 2) {
			Position beforeRook = new Position(before.getRow(), before.getCol() - 4);
			Position afterRook = new Position(before.getRow(), before.getCol() - 1);
			ChessPiece closeRook = (ChessPiece) board.removePiece(afterRook);
			board.placePiece(closeRook, beforeRook);
			closeRook.decreaseMove();
		}
	}
	
	private Color enemy(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private ChessPiece king(Color color) {
		List<Piece> list = piecesAvaible.stream().filter(x -> ((ChessPiece) x).getColor() == color).collect(Collectors.toList());
		
		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece) p;
			}
		}
		
		throw new IllegalStateException("King of color " + color + " isn't on the board.");
	}
	
	private boolean testCheck(Color color) {
		Position kingPos = king(color).getChessPosition().toPosition();
		List<Piece> enemies = piecesAvaible.stream().filter(x -> ((ChessPiece) x).getColor() == enemy(color)).collect(Collectors.toList());
		
		for (Piece p : enemies) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPos.getRow()][kingPos.getCol()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) {
			return false;
		}
		
		List<Piece> enemies = piecesAvaible.stream().filter(x -> ((ChessPiece) x).getColor() == enemy(color)).collect(Collectors.toList());
		for (Piece p : enemies) {
			boolean[][] mat = p.possibleMoves();
			
			for(int a = 0; a < board.getRows(); a++) {
				for(int b = 0; b < board.getRows(); b++) {
					if(mat[a][b]) {
						Position before = ((ChessPiece) p).getChessPosition().toPosition();
						Position after = new Position(a, b);
						Piece captured = makeMove(before, after);
						boolean testCheck = testCheck(color);
						undoCheck(before, after, captured);
						
						if(!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	private void placeNewPiece(char col, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(col, row).toPosition());
		piecesAvaible.add(piece);
	}
	
	private void initialSetup() {
		//white
        placeNewPiece('E', 1, new King(board, Color.WHITE, this));
        placeNewPiece('D', 1, new Queen(board, Color.WHITE));
        
		placeNewPiece('A', 1, new Rook(board, Color.WHITE));
        placeNewPiece('H', 1, new Rook(board, Color.WHITE));
        
        placeNewPiece('C', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('F', 1, new Bishop(board, Color.WHITE));
        
        placeNewPiece('B', 1, new Knight(board, Color.WHITE));
        placeNewPiece('G', 1, new Knight(board, Color.WHITE));
        
        placeNewPiece('A', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('B', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('C', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('D', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('E', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('F', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('G', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('H', 2, new Pawn(board, Color.WHITE));

        //black
        placeNewPiece('E', 8, new King(board, Color.BLACK, this));
        placeNewPiece('D', 8, new Queen(board, Color.BLACK));

        placeNewPiece('A', 8, new Rook(board, Color.BLACK));
        placeNewPiece('H', 8, new Rook(board, Color.BLACK));
        
        placeNewPiece('C', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('F', 8, new Bishop(board, Color.BLACK));
        
        placeNewPiece('B', 8, new Knight(board, Color.BLACK));
        placeNewPiece('G', 8, new Knight(board, Color.BLACK));

        placeNewPiece('A', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('B', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('C', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('D', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('E', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('F', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('G', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('H', 7, new Pawn(board, Color.BLACK));
        
	}
}
