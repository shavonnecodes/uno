package com.improving;

import com.improving.exceptions.EndGameException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Player implements IPlayer {
    private String name;
    private List<Card> handCards = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public List<Card> getHandCards() {
        return handCards;
    }

    @Override
    public void takeTurn(Game game) throws EndGameException {
        if (handSize() == 1) {
            System.out.println("UNNNOOOO BIIIITTTCHHHHH");
        }

        for (var playerCard : getHandCards()) {
            if (game.isPlayable(playerCard)) {
                System.out.println("");
                System.out.println(playerCard + " was just played.");
                System.out.println("--END TURN--");
                System.out.println("~*~*~*~*~*~*~*~*~*~*~");
                playCard(playerCard, game);
                return;
            }
        }
        System.out.println("Can't play! Gotta draw!");
        System.out.println("");
        var newCard = draw(game);
        System.out.println("[" + newCard.toString() + "] was drawn!");
        if (game.isPlayable(newCard)) {
            playCard(newCard, game);
            System.out.println("The card was played - HOT DAMN!");
            System.out.println("--END TURN--");
            System.out.println("~*~*~*~*~*~*~*~*~*~*~");
        } else {
            System.out.println("--END TURN--");
            System.out.println("~*~*~*~*~*~*~*~*~*~*~");
        }
    }

    @Override
    public int handSize() {
        return handCards.size();
    }

    @Override
    public Card draw(Game game) {
        var drawnCard = game.draw();
        handCards.add(drawnCard);
        return drawnCard;
    }

    public void playCard(Card card, Game game) {
        Colors declaredColor = declareColor(card, game);
        handCards.remove(card);
        game.playCard(card, Optional.ofNullable(declaredColor));
    }


    public Colors declareColor(Card card, Game game) {
        var declaredColor = card.getColor();
        if (card.getColor().toString().equals("Wild")) {
            List<Colors> randomColors = new ArrayList<>();
            randomColors.add(Colors.Red);
            randomColors.add(Colors.Blue);
            randomColors.add(Colors.Green);
            randomColors.add(Colors.Yellow);
//            declaredColor = randomColors.get(0);
            boolean declaredColorinHand = false;
            int numWildColorCardsInHand = 0;

            if (card.getColor().equals(Colors.Wild)) {
                while (declaredColorinHand) {
                    Collections.shuffle(randomColors);
                    for (Card c : handCards) {
                        if (card.getColor().equals(randomColors.get(0))) {
                            declaredColorinHand = true;
                            declaredColor = card.getColor();
                            break;
                        }
                        if (card.getColor().equals(Colors.Wild)) {
                            numWildColorCardsInHand++;
                        }
                        if (numWildColorCardsInHand == handSize()) {
                            Collections.shuffle(randomColors);
                            declaredColorinHand = true;
                            declaredColor = randomColors.get(0);
                        }
                    }
                }
            }
        }
        return declaredColor;
    }

    @Override
    public String toString() {
        return name;
    }
}


//    public Card takeTurn(Game game) throws EndGameException {
//        for (var playerCard : getHandCards()) {
//            if (maybePlayCard(playerCard, game)) {
//                playCard(playerCard, game);
//                return playerCard;
//            }
//        }
//
//        System.out.println("Can't play! Gotta draw!");
//        var newCard = game.deck.draw();
//        draw(game);
//        if (maybePlayCard(newCard, game)) {
//            playCard(newCard, game);
//            return newCard;
//        }
//        return null;
//    }

//    private boolean maybePlayCard(Card playerCard, Game game) {
//        if (playerCard.getColor().getColorName().equals(game.deck.getDiscardPile().get(game.deck.getDiscardPile().size() - 1).getColor().getColorName())
//                || playerCard.getFace().equals(game.deck.getDiscardPile().get(game.deck.getDiscardPile().size() - 1).getFace())
//                || playerCard.getColor().equals(Colors.Wild)
//                || game.deck.getDiscardPile().get(game.deck.getDiscardPile().size() - 1).getColor().equals(Colors.Wild)) {
//            playCard(playerCard, game);
//            System.out.println("");
//            System.out.println(playerCard + " was just played.");
//            System.out.println(getHandCards().toString() + ": " + handSize() + " cards in hand.");
//
//            if (handSize() == 1) {
//                System.out.println("UUUNNNNOOOOO");
//            }
//
//            if (handSize() == 0) {
//                throw new EndGameException();
//            }
//            return true;
//        }
//        return false;
//    }

