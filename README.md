# XXLChess

XXLChess is a customizable Java-based chess game with an AI opponent. It features an expanded board and additional chess pieces beyond the standard set.

## Features

- Customizable game settings via config.json
- AI opponent
- Expanded 14x14 board
- Additional chess pieces: Amazon, Archbishop, Camel, Chancellor, and General
- Time control for both player and AI
- Interactive GUI using Processing

## Game Pieces

The game includes standard chess pieces as well as:
- Amazon
- Archbishop
- Camel
- Chancellor
- General

Each piece has its unique movement and capture rules.

## How to Play

1. Start the game
2. Use mouse clicks to select and move pieces
3. Use keyboard inputs for additional controls (specifics to be added based on implementation)

## Configuration

Game settings can be customized in the `config.json` file, including:
- Player's color
- Time control settings for both player and AI

## Technical Details

- Implemented in Java using Object-Oriented Programming principles
- Uses Processing for GUI
- Main game logic handled by the `Board` class
- Each piece type is a separate class inheriting from the `Piece` class
- Utilizes factory pattern for piece creation (`PieceFactory`)

## Class Structure

- `Piece`: Base class for all chess pieces
- `Board`: Manages game state and rules
- `App`: Handles GUI and user interactions
- `Coordinate`, `Color`, `Constants`: Utility classes
- `Block`, `Owner`, `GAME_STATUS`: Additional game entities

## AI

The AI opponent is implemented in the `Board` class, using the `AIMove` method to determine its moves.

## Game End Conditions

The game can end in the following ways:
- Checkmate
- Draw
- Time-out

## Future Improvements

- Different game modes
- Advanced AI strategies
- Custom piece sets
