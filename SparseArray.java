/**
 * @author Amosen
 * @create 2020-06-12 11:15
 */
/*
稀疏数组的实现：
稀疏数组：
是一个m行3列的二维数组，m的值由具体的二维数组而定
本质上，是使用一个占用内存较小的数组a来代表另一个占用内存较小的数组b，数组b一般其中含有较多的相同元素

稀疏数组的第一行存储的数据意义为：原数组的行数、列数以及有效数据的个数（不同的值的个数）
其它行分别存储每个不同的值的信息（所在的行、列以及值）

原数组：                                        转换后的稀疏数组：
0   0   0   0                               [0] 4   4   2
0   1   0   0               -------->       [1] 1   1   1
0   0   0   0                               [2] 3   3   9
0   0   0   9
 */
class SparseArrayTest {
    public static void main(String[] args) {
        int[][] handleArr = new int[5][9];
        handleArr[3][1] = 6;
        handleArr[2][4] = 4;
        int[][] handledArr = SparseArray.getSparseArray(handleArr);
        int[][] newHandleArr = SparseArray.getSourceArray(handledArr);

        System.out.println("原数组为：");
        for(int i = 0; i < handleArr.length; i++) {
            for(int j = 0; j < handleArr[0].length; j++) {
                System.out.print(handleArr[i][j] + "\t");
            }
            System.out.println();
        }

        System.out.println("对应的稀疏数组为：");
        for(int i = 0; i < handledArr.length; i++) {
            for(int j = 0; j < handledArr[0].length; j++) {
                System.out.print(handledArr[i][j] + "\t");
            }
            System.out.println();
        }

        System.out.println("还原后的数组为：");
        for(int i = 0; i < newHandleArr.length; i++) {
            for(int j = 0; j < newHandleArr[0].length; j++) {
                System.out.print(newHandleArr[i][j] + "\t");
            }
            System.out.println();
        }
    }
}

//将稀疏数组的实现和还原封装为类：
public class SparseArray {
    //将数组转换为稀疏数组
    public static int[][] getSparseArray(int[][] sourceArr) {
        if(sourceArr == null || sourceArr.length == 0) {
            return null;
        }

        int sum = 0;
        for(int[] row: sourceArr) {
            for(int data: row) {
                if(data != 0) {
                    sum++;
                }
            }
        }

        int[][] sparseArr = new int[sum+1][3];
        sparseArr[0][0] = sourceArr.length;
        sparseArr[0][1] = sourceArr[0].length;
        sparseArr[0][2] = sum;

        int count = 0;
        for(int i = 0; i < sourceArr.length; i++) {
            for(int j = 0; j < sourceArr[0].length; j++) {
                if(sourceArr[i][j] != 0) {
                    count++;
                    sparseArr[count][0] = i;
                    sparseArr[count][1] = j;
                    sparseArr[count][2] = sourceArr[i][j];
                }
                if(count == sum) {
                    break;
                }
            }
            if(count == sum) {
                break;
            }
        }
        return sparseArr;
    }

    //将稀疏数组还原
    public static int[][] getSourceArray(int[][] sparseArr) {
        if(sparseArr == null || sparseArr.length == 0) {
            return null;
        }
        int[][] destArr = new int[sparseArr[0][0]][sparseArr[0][1]];
        for(int i = 1; i < sparseArr.length; i++) {
            destArr[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
        }
        return destArr;
    }
}
