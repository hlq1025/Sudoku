package com.example.hua.Sudoku;
import java.util.HashSet;
import java.util.Set;
public class Sudoku {
    public static char[][]board;
    public static char[][] test_board;
    public static boolean  solveSudoku(char[][] board) {
        // 三个布尔数组 表明 行, 列, 还有 3*3 的方格的数字是否被使用过
        boolean[][] rowUsed = new boolean[9][10];
        boolean[][] colUsed = new boolean[9][10];
        boolean[][][] boxUsed = new boolean[3][3][10];
        // 初始化
        for(int row = 0; row < board.length; row++){
            for(int col = 0; col < board[0].length; col++) {
                int num = board[row][col] - '0';
                if(1 <= num && num <= 9){
                    rowUsed[row][num] = true;
                    colUsed[col][num] = true;
                    boxUsed[row/3][col/3][num] = true;
                }
            }
        }
        // 递归尝试填充数组
       return recusiveSolveSudoku( board,rowUsed, colUsed, boxUsed, 0, 0);
    }
    public static boolean CanGetSolution()
    {
        for(int row=0;row<9;row++)
            for(int col=0;col<9;col++)
            {
                test_board[row][col]=board[row][col];
            }



       return  isValidSudoku(test_board)&&solveSudoku(test_board);
    }
    public static int GetHint(int row,int col)
    {
        int ans=1;

        for(int num=1;num<=9;num++)
            {for(int i=0;i<9;i++)
                for(int j=0;j<9;j++)
                {
                    test_board[i][j]=board[i][j];
                }


                test_board[row][col]=(char) (num+48);
                if(isValidSudoku(test_board)&&solveSudoku(test_board))
                {
                   return num;
                }
            }
            return ans;
    }
    public static boolean isValidSudoku(char[][] board) {
        // 记录某行，某位数字是否已经被摆放
        boolean[][] row = new boolean[9][9];
        // 记录某列，某位数字是否已经被摆放
        boolean[][] col = new boolean[9][9];
        // 记录某 3x3 宫格内，某位数字是否已经被摆放
        boolean[][] block = new boolean[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    int num = board[i][j] - '1';
                    int blockIndex = i / 3 * 3 + j / 3;
                    if (row[i][num] || col[j][num] || block[blockIndex][num]) {
                        return false;
                    } else {
                        row[i][num] = true;
                        col[j][num] = true;
                        block[blockIndex][num] = true;
                    }
                }
            }
        }
        return true;
    }
    public static void InitSudoku()
    {

                /*{'.','.','.','.','.','2','.','.','0'};
                {'.','7','8','.','.','.','3','0','0'}
                {'.','.','.','.','.','4','.','.','.'},
                {'5','.','.','.','0','0','0','0','0'},
                {'.','.','.','.','.','.','1','.','.'},
                {'.','.','.','.','3','.','7','.','8'},
                {'2','.','.','.','.','.','.','4','.'},
                {'.','.','.','.','.','5','.','9','.'},
                {'.','1','.','.','7','.','.','.','.'}*/
                board=new char[9][9];
                test_board=new char[9][9];
                Set<Integer> s=new HashSet<Integer>();
                for(int col=0;col<9;col++) {
                    Integer random_number = 9;
                    while (true) {
                        random_number = (Integer) (int) (Math.random() * 9 + 1);
                        if (!s.contains(random_number)) {
                            board[0][col] = (char) (48 + random_number);
                            s.add(random_number);
                            break;
                        }

                    }

                }
                for(int row=1;row<9;row++)
                    for(int col=0;col<9;col++)
                    {
                        board[row][col]='.';
                    }

        solveSudoku(board);

        for(int row=0;row<9;row++)
            for(int col=0;col<9;col++)
            {
                double p=Math.random();
                if(p<0.5)
                {
                    board[row][col]='.';
                }
            }
            return ;
    }

    private static boolean recusiveSolveSudoku( char[][] board, boolean[][]rowUsed, boolean[][]colUsed, boolean[][][]boxUsed, int row, int col){
        // 边界校验, 如果已经填充完成, 返回true, 表示一切结束

        if(col == board[0].length){
            col = 0;
            row++;
            if(row == board.length){
                return true;
            }
        }
        // 是空则尝试填充, 否则跳过继续尝试填充下一个位置
        if(board[row][col] == '.') {


            // 尝试填充1~9
            for(int num = 1; num <= 9; num++){
                boolean canUsed = !(rowUsed[row][num] || colUsed[col][num] || boxUsed[row/3][col/3][num]);
                if(canUsed){
                    rowUsed[row][num] = true;
                    colUsed[col][num] = true;
                    boxUsed[row/3][col/3][num] = true;

                    board[row][col] = (char)('0' + num);
                    if(recusiveSolveSudoku(board, rowUsed, colUsed, boxUsed, row, col + 1)){
                        return true;
                    }
                    board[row][col] = '.';

                    rowUsed[row][num] = false;
                    colUsed[col][num] = false;
                    boxUsed[row/3][col/3][num] = false;
                }
            }


        }
        else
            {
            return recusiveSolveSudoku( board,rowUsed, colUsed, boxUsed, row, col + 1);
        }
        return false;
    }


}
