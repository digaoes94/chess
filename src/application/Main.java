package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.exceptions.ChessException;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		ChessMatch match = new ChessMatch();
		List<ChessPiece> captures = new ArrayList<ChessPiece>();
		
		while (!match.getCheckMate()) {
			try {
				UI.clearScreen();
				
				UI.printMatch(match, captures);
				System.out.println();
				System.out.print("Source: ");
				ChessPosition before = UI.readChessPosition(scan);
				
				boolean[][] possibleMoves = match.possibleMoves(before);
				UI.clearScreen();
				UI.printBoard(match.getPieces(), possibleMoves);
				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(scan);
				
				ChessPiece captured = match.movePiece(before, target);
				if (captured != null) {
					captures.add(captured);
				}
			}
			catch (ChessException e) {
				System.out.println(e.getMessage());
				scan.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				scan.nextLine();
			}
		}
		
		UI.clearScreen();
		UI.printMatch(match, captures);
	}
}