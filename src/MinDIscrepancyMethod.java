public class MinDIscrepancyMethod {

    private double matrA[][];
    private double vectorB[];
    private double prevX[];
    private double curX[];
    private double curR[];
    private double tao;
    private double epsilon = 0.00001;
    private double discrepancy[];
    private int count;
    private int n;


    public MinDIscrepancyMethod(double matrA[][], double vectorB[]){
        n = matrA.length;
        count = 0;
        this.vectorB = new double[n];
        prevX = new double[n];
        curX = new double[n];
        curR = new double[n];
        this.matrA = new double[n][n];
        for(int i = 0; i < n; i++){
            this.vectorB[i] = vectorB[i];
            prevX[i] = this.vectorB[i] / matrA[i][i];
            for(int j = 0; j < n; j++){
                this.matrA[i][j] = matrA[i][j];
            }
        }
    }

    public void createSymmetricMatr(){
        double symmetricMatr[][] = new double[n][n];
        for(int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                symmetricMatr[i][j] = 0;
                for (int k = 0; k < n; k++) {
                    symmetricMatr[i][j] += matrA[k][i] * matrA[k][j];
                }
            }
        }
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                matrA[i][j] = symmetricMatr[i][j];
            }
        }
    }

    public void createNewVectorB(){
        double newVector[] = new double[n];
        for(int i=0; i<n; i++)
        {
            newVector[i]=0;
            for(int j=0; j<n; j++)
            {
                newVector[i] += matrA[j][i]*vectorB[j];
            }
        }
        for(int i = 0; i < n; i++){
            vectorB[i] = newVector[i];
        }

    }

    private double difference(){
        double diff = Math.abs(prevX[0] - curX[0]);
        for(int i = 0; i < n; i++){
            if(Math.abs(prevX[i] - curX[i]) > diff){
                diff = prevX[i] - curX[i];
            }
        }
        return Math.abs(diff);
    }

    private void newPrevX(){
        for(int i = 0; i < n; i++){
            prevX[i] = curX[i];
        }
    }

    private double vectorsMultiply(double first[], double second[]) {
        double res = 0;
        for (int i = 0; i < n; i++){
            res += first[i]*second[i];
            i++;
        }
        return res;
    }


    private double[] multiplyMatrWithVector(double matr[][], double vector[]){
        double resVector[] = new double[n];
        for(int i=0; i<n; i++)
        {
            resVector[i]=0;
            for(int j=0; j<n; j++)
            {
                resVector[i] += matr[i][j]*vector[j];
            }
        }
        return resVector;
    }

    private void newTao(){
        tao = vectorsMultiply(multiplyMatrWithVector(matrA, curR), curR) /
                vectorsMultiply(multiplyMatrWithVector(matrA, curR), multiplyMatrWithVector(matrA, curR));
    }

    private void newCurR(){
        for(int i = 0; i < n; i++){
            curR[i] = multiplyMatrWithVector(matrA, curX)[i] - vectorB[i];
        }
    }

    public void minDiscrepancyMethod() {
        createNewVectorB();
        createSymmetricMatr();
        while (difference() > epsilon) {
            count++;
            newCurR();
            newTao();
            newPrevX();
            for (int i = 0; i < n; i++) {
                curX[i] = prevX[i] - tao*curR[i];
            }
        }
    }
    public void createDiscrepancy ( double matrA[][], double vectorB[]){
        discrepancy = new double[n];
        double[] res = new double[n];
        for (int i = 0; i < n; i++) {
            res[i] = 0;
            for (int j = 0; j < n; j++) {
                res[i] += matrA[i][j] * curX[j];
            }
            discrepancy[i] = vectorB[i] - res[i];
        }
    }

    public void print () {
        System.out.println("Vector X");
        for (double item : curX) {
            System.out.format("%25s", item + "    ");
        }
        System.out.println("count = " + count);
    }
    public void printDiscrepancy () {
        System.out.println("Discrepancy");
        for (double item : discrepancy) {
            System.out.format("%25s", item + "    ");
        }
        System.out.println();
    }

    public void printAll(){
        System.out.println();
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                System.out.format("%25s", matrA[i][j] + "    ");
            }
            System.out.format("%25s", vectorB[i] + "\n");
        }
    }


}
