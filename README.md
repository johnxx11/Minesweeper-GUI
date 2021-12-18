# csc-335-minesweeper-csc335-jingyu-yanxu-qi-zhenyi
csc-335-minesweeper-csc335-jingyu-yanxu-qi-zhenyi created by GitHub Classroom

This is a simple implementation of the Minesweeper game using JavaFX. It has the following features:<br><br>
(1) An N by M grid containing K mines randomly placed(N = 16, M = 16, K = 40 as default settings)<br>
(2) Each click either reveals a mine (ending the game) or an integer that indicates the number of<br>
mines in the surrounding 8 squares.<br>
(3) Automatically reveals contiguous regions of “zero” squares<br>
(4) A timer that indicates the elapsed time<br>
(5) A high score board that is updated with the fastest times for standard grid sizes<br>
(6) The first square clicked is never a mine<br>
(7) Marking of suspected mine squares prevents accidentally clicking on them(Right click mouse to flag a mine)<br>
(8) Game is won when the mined squares are the only ones left unrevealed<br>
(9) Save/resume of in-progress games<br>
(10) Networked multiplayer–oneplayer places the mines,the other solves the board(like battleship)
