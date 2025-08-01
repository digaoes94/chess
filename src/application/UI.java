package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI {
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	public static void clearScreen() {
		 System.out.print("\033[H\033[2J");
		 System.out.flush();
	}
	
	public static ChessPosition readChessPosition(Scanner scan) {
		try {
			String s = scan.nextLine();
			char col = Character.toUpperCase(s.charAt(0));
			int row = Integer.parseInt(s.substring(1));
			return new ChessPosition(col, row);
		}
		catch (RuntimeException e) {
			throw new InputMismatchException("Error reading position. Valid positions are between A1 and H8.");
		}
	}
	
	public static void printMatch(ChessMatch match, List<ChessPiece> captures) {
		printBoard(match.getPieces());
		System.out.println();
		printCaptures(captures);
		System.out.println();
		System.out.println("Turn: " + match.getTurn());
		
		if(!match.getCheckMate()) {
			System.out.println("Waiting for the " + match.getCurrentPlayer() + " player's move.");
			if(match.getCheck()) {
				System.out.println("CHECK!");
			}
		}
		else {
			System.out.println("CHECKMATE!");
			System.out.println("Winner: " + match.getCurrentPlayer());
		}
		
	}
	
	public static void printBoard(ChessPiece[][] pieces) {
		for (int a = 0; a < pieces.length; a++) {
			System.out.print((8 - a) + " ");
			
			for(int b = 0; b < pieces.length; b++) {
				printPiece(pieces[a][b], false);
			}
			System.out.println();
		}
		
		System.out.println("  A B C D E F G H");
	}
	
	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
		for (int a = 0; a < pieces.length; a++) {
			System.out.print((8 - a) + " ");
			
			for(int b = 0; b < pieces.length; b++) {
				printPiece(pieces[a][b], possibleMoves[a][b]);
			}
			System.out.println();
		}
		
		System.out.println("  A B C D E F G H");
	}
	
	private static void printPiece(ChessPiece piece, boolean background) {
		if (background) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
    	if (piece == null) {
            System.out.print("-" + ANSI_RESET);
        }
        else {
            if (piece.getColor() == Color.WHITE) {
                System.out.print(ANSI_WHITE + piece + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
            }
        }
        System.out.print(" ");
	}
	
	private static void printCaptures(List<ChessPiece> captures) {
		List<ChessPiece> whites = captures.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
		List<ChessPiece> blacks = captures.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());
		System.out.println("Captured pieces");
		System.out.println("White: " + ANSI_WHITE + Arrays.toString(whites.toArray()) + ANSI_RESET);
		System.out.println("Black: " + ANSI_YELLOW + Arrays.toString(blacks.toArray()) + ANSI_RESET);
	}
}
