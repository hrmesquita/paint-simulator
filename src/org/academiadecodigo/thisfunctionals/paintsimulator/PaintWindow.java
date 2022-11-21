package org.academiadecodigo.thisfunctionals.paintsimulator;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

import java.io.*;

public class PaintWindow implements KeyboardHandler {

    FileReader inputStream;
    FileWriter outputStream;

    Rectangle brush = new Rectangle(30, 30, 10, 10);

    PaintWindow(){
        startKeyboard();
    }

    Rectangle[] grid = new Rectangle[900];

    public void initialize() {

        Rectangle rectangle = new Rectangle(0, 0, 300, 300);

        brush.setColor(Color.GREEN);
        brush.fill();

        rectangle.draw();

        int currentY = 0;
        int currentX = 0;

        for (int i = 0; i < grid.length; i++) {
            grid[i] = new Rectangle(currentX, currentY, 10, 10);
            currentX += 10;

            if (currentX == 300) {
                currentX = 0;
                currentY += 10;
            }

            grid[i].draw();
        }

    }

    private void startKeyboard() {

        Keyboard keyboard = new Keyboard(this);

        KeyboardEvent rightPressed = new KeyboardEvent();
        rightPressed.setKey(KeyboardEvent.KEY_RIGHT);
        rightPressed.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        KeyboardEvent downPressed = new KeyboardEvent();
        downPressed.setKey(KeyboardEvent.KEY_DOWN);
        downPressed.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        KeyboardEvent upPressed = new KeyboardEvent();
        upPressed.setKey(KeyboardEvent.KEY_UP);
        upPressed.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        KeyboardEvent leftPressed = new KeyboardEvent();
        leftPressed.setKey(KeyboardEvent.KEY_LEFT);
        leftPressed.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        KeyboardEvent spacePressed = new KeyboardEvent();
        spacePressed.setKey(KeyboardEvent.KEY_SPACE);
        spacePressed.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        KeyboardEvent SPressed = new KeyboardEvent();
        SPressed.setKey(KeyboardEvent.KEY_S);
        SPressed.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        KeyboardEvent CPressed = new KeyboardEvent();
        CPressed.setKey(KeyboardEvent.KEY_C);
        CPressed.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        KeyboardEvent LPressed = new KeyboardEvent();
        LPressed.setKey(KeyboardEvent.KEY_L);
        LPressed.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        keyboard.addEventListener(rightPressed);
        keyboard.addEventListener(downPressed);
        keyboard.addEventListener(upPressed);
        keyboard.addEventListener(leftPressed);
        keyboard.addEventListener(spacePressed);
        keyboard.addEventListener(SPressed);
        keyboard.addEventListener(CPressed);
        keyboard.addEventListener(LPressed);

    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {

        switch (keyboardEvent.getKey()) {
            case (KeyboardEvent.KEY_LEFT):
                if (!(brush.getX() <= 0)) {
                    brush.translate(-10, 0);
                }
                break;

            case (KeyboardEvent.KEY_RIGHT):
                if (brush.getX() < 290) {
                    brush.translate(10, 0);
                }
                break;

            case (KeyboardEvent.KEY_UP):
                if (brush.getY() > 0) {
                    brush.translate(0, -10);
                }
                break;

            case (KeyboardEvent.KEY_DOWN):
                if (brush.getY() < 290) {
                    brush.translate(0, 10);
                }
                break;

            case (KeyboardEvent.KEY_C):
                for (Rectangle square : grid) {
                    square.delete();
                    square.draw();
                }
                break;

            case (KeyboardEvent.KEY_S):
                String dataFiller = "";
                try {
                    outputStream = new FileWriter("isFilled.txt");
                    for (Rectangle square : grid) {
                        if (square.isFilled()) {
                            dataFiller += "1";
                        } else {
                            dataFiller += "0";
                        }
                    }
                    System.out.println(dataFiller);

                    outputStream.write(dataFiller);
                    outputStream.close();

                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            case (KeyboardEvent.KEY_L):

                try {
                    char[] charArr = new char[900];
                    inputStream = new FileReader("isFilled.txt");

                    while (inputStream.read(charArr) != -1) {

                        for (int j = 0; j < grid.length; j++) {
                            if (Character.getNumericValue(charArr[j]) == 1) {
                                grid[j].delete();
                                grid[j].fill();
                            }
                        }

                    }

                    inputStream.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case (KeyboardEvent.KEY_SPACE):

                for (Rectangle square : grid) {
                    if (square.getX() == brush.getX() && square.getY() == brush.getY()) {
                        if (!square.isFilled()) {
                            square.fill();
                        } else {
                            square.delete();
                            square.draw();
                        }
                    }
                }
                break;
        }

    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {

    }
}
