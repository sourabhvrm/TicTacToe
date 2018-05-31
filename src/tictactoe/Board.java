/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.util.*;

/**
 *
 * @author saurabh
 */
public class Board {
    List<Point> availableMoves;
    List<PointsandScores> ChildrensScoresandPoints;
    byte PLAYER;
    byte CPU;
    int[][] board=new int[3][3];

    public Board() {
        PLAYER=1;
        CPU=-1;
    }
    
    public boolean isGameOver(){
        return (hasPWon(-1)||hasPWon(1)||getAvailableMoves().isEmpty());
    }
    
    public boolean hasPWon(int p){
        if(isEqual(board[0][0],board[1][1], board[2][2], p))return true;
        else if(isEqual(board[0][2], board[1][1], board[2][0], p))return true;
        for(int i=0;i<3;i++){
            if(isEqual(board[0][i], board[1][i], board[2][i], p))return true;
            if(isEqual(board[i][0], board[i][1], board[i][2], p))return true;
        }
    return false;
    }
    
    public boolean isEqual(int x,int y,int z,int val){
        return x==y&&y==z&&z==val;
    }
    
    public List<Point> getAvailableMoves(){
        availableMoves=new ArrayList<>();
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(board[i][j]==0){
                    availableMoves.add(new Point(i,j));
                }
            }
        }
        return availableMoves;
    }
    
    public void makeMove(Point move,int p){
        board[move.x][move.y]=p;
    }
    
    public Point returnBestMove(){
        int MIN=Integer.MAX_VALUE;
        int best=0;
        for(int i=0;i<ChildrensScoresandPoints.size();i++){
            if(MIN>ChildrensScoresandPoints.get(i).score){
                MIN=ChildrensScoresandPoints.get(i).score;
                best=i;
            }
        }
        return ChildrensScoresandPoints.get(best).point;
    }
    
    public int returnMin(List<Integer> list){
        int min=Integer.MAX_VALUE;
        int index=-1;
        for(int i=0;i<list.size();i++){
            if(list.get(i)<min){
                min=list.get(i);
                index=i;
            }
        }
        return list.get(index);
    }
    
    public int returnMax(List<Integer> list){
        int max=Integer.MIN_VALUE;
        int index=-1;
        for(int i=0;i<list.size();i++){
            if(list.get(i)>max){
                max=list.get(i);
                index=i;
            }
        }
        return list.get(index);
    }
    
    public int minimax(int depth,int turn){
        if(hasPWon(PLAYER))return 20-depth*3;
        if(hasPWon(CPU))return -20+depth*3;
        List<Point> availablePoints=getAvailableMoves();
        if(availablePoints.isEmpty())return 0;
        
        List<Integer> scores=new ArrayList<>();
        
        for(int i=0;i<availablePoints.size();i++){
            Point point=availablePoints.get(i);
            if(turn==PLAYER){
                makeMove(point, PLAYER);
                int currentscore=minimax(depth+1,CPU);
                scores.add(currentscore);
                if(depth==0)ChildrensScoresandPoints.add(new PointsandScores(currentscore, point));
            }
            else if(turn==CPU){
                makeMove(point, CPU);
                int currentscore=minimax(depth+1,PLAYER);
                scores.add(currentscore);
                if(depth==0)ChildrensScoresandPoints.add(new PointsandScores(currentscore, point));
            }
            board[point.x][point.y]=0;
        }
        return turn==PLAYER?returnMax(scores):returnMin(scores);
    }
    
    public void callMiniMax(int depth,int turn){
        ChildrensScoresandPoints=new ArrayList<>();
        minimax(depth,turn);
    }
}
