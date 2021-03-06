package com.windhorsesoftware.client;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.windhorsesoftware.tictactoe.*;

import com.windhorsesoftware.tictactoe.GameView;

public class GamePresenterTest {
	GameView mockView = mock(GameView.class);

	@Test
	public void whenUserSelectsOccupiedCellViewInformsOfError() throws Exception {
		Board board = new Board(3, 3);
		Position position = Position.getPosition(0, 0);
		board.setCell(position, Mark.O);
		
		GamePresenter presenter = new GamePresenter(mockView, board);
		presenter.markPositionForPlayer(position, Mark.X);
		
		verify(mockView).cellIsOccupiedWarning(position);
	}
	
	@Test
	public void whenUserSelectsEmptyCellItModifiesTheBoardAndInformsTheViewOfSuccess() throws Exception {
		
		GamePresenter presenter = new GamePresenter(mockView, new Board(3, 3));
		Position position = Position.getPosition(0,  0);
		presenter.markPositionForPlayer(position, Mark.X);

		assertThat(presenter.getBoard().getCell(position), equalTo(Mark.X));
		verify(mockView).setCellOccupied(position, Mark.X);
	}
	
	@Test
	public void whenUserWinsAGameItInformsTheViewAndResetsTheBoardAndView() throws Exception {
		Board board = BoardBuilder.makeBoard(
				"X X _",
				"_ _ _",
				"_ _ _");

		GamePresenter presenter = new GamePresenter(mockView, board);
		Position position = Position.getPosition(0,  2);
		
		presenter.markPositionForPlayer(position, Mark.X);

		verify(mockView).gameWasWon(Mark.X);
		assertThat(presenter.getBoard().getCell(position), equalTo(Mark.EMPTY));
		
		verify(mockView).resetView();
	}
	
	@Test
	public void whenThereIsADrawItInformsTheViewAndResetsTheBoardAndView() throws Exception {
		Board board = BoardBuilder.makeBoard(
				"_ X O",
				"O X X",
				"X O O");

		GamePresenter presenter = new GamePresenter(mockView, board);
		Position position = Position.getPosition(0,  0);
		
		presenter.markPositionForPlayer(position, Mark.O);

		verify(mockView).gameWasADraw();
		assertThat(presenter.getBoard().getCell(position), equalTo(Mark.EMPTY));
		
		verify(mockView).resetView();
	}
	
	@Test
	public void startsWithPlayerXAndAlternatesToO() throws Exception {
		Board board = BoardBuilder.makeBoard(
				"_ _ _",
				"_ _ _",
				"_ _ _");

		GamePresenter presenter = new GamePresenter(mockView, board);
		Position position = Position.getPosition(0,  0);
		presenter.positionClicked(position);

		Position secondPosition = Position.getPosition(0,  1);
		presenter.positionClicked(secondPosition);
		
		assertThat(presenter.getBoard().getCell(secondPosition), equalTo(Mark.O));
	}
}
