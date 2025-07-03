package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.exceptions.ChessException;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		ChessMatch match = new ChessMatch();
		
		while (true) {
			try {
				UI.clearScreen();
				
				UI.printMatch(match);
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
	}
}