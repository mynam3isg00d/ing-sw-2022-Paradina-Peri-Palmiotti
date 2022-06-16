package Observer.View.CLI;

import Model.IslandsWrapper;

import java.util.Arrays;

public class CLIIslandWrapper {

    private IslandsWrapper islandsWrapper;
    private CLIIsland[] cliIslands;

    public CLIIslandWrapper(IslandsWrapper iw) {
        islandsWrapper = iw;

        updateIslandArray();
    }

    public void updateIslandWrapper(IslandsWrapper islandsWrapper) {
        this.islandsWrapper = islandsWrapper;

        updateIslandArray();
    }

    public int getX() {
        return 5 * cliIslands[0].getX();
    }

    public int getY() {
        return 3 * cliIslands[0].getY();
    }

    public void display(int x0, int y0) {

        int ix = cliIslands[0].getX();
        int iy = cliIslands[0].getY();


        for(int i=0; i<cliIslands.length; i++) {
            if (cliIslands[i] != null) {
                if(i == 0) cliIslands[i].displayLines(x0, y0 + iy);
                if(i > 0 && i < 6) cliIslands[i].displayLines(x0 + (i-1)*ix, y0);
                if(i == 6) cliIslands[i].displayLines(x0 + 4*ix, y0 + iy);
                if(i > 6) cliIslands[i].displayLines(x0 + (11-i)*ix, y0 + 2*iy);
            } else {
                CLIEmpty e = new CLIEmpty(ix, iy);
                if(i == 0) e.displayLines(x0, y0 + iy);
                if(i > 0 && i < 6) e.displayLines(x0 + (i-1)*ix, y0);
                if(i == 6) e.displayLines(x0 + 4*ix, y0 + iy);
                if(i > 6) e.displayLines(x0 + (11-i)*ix, y0 + 2*iy);
            }
        }
    }

    private void updateIslandArray() {
        cliIslands = new CLIIsland[12];
        Arrays.fill(cliIslands, null);
        int wrapperLength = islandsWrapper.getIslandLength();
        int offset = cliIslands.length/wrapperLength;

        for(int i=0; i<wrapperLength; i++) {
            cliIslands[i*offset] = new CLIIsland(islandsWrapper.getIsland(i), i);
        }
    }

    public void setNull(int i) {cliIslands[i] = null;}
}
