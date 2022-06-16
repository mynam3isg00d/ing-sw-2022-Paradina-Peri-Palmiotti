package View.CLI;

import Model.CloudWrapper;

public class CLICloudWrapper {

    private CloudWrapper cloudWrapper;
    private CLICloud[] cliClouds;

    public CLICloudWrapper(CloudWrapper cloudWrapper) {
        this.cloudWrapper = cloudWrapper;

        updateCloudArray();
    }

    public void updateCloudWrapper(CloudWrapper cloudWrapper) {
        this.cloudWrapper = cloudWrapper;
        updateCloudArray();
    }

    public void display(int x0, int y0) {
        if (cloudWrapper.getNumOfClouds() == 2) {

            int cy = cliClouds[0].getY();
            cliClouds[0].displayLines(x0, y0 + cy/2);
            cliClouds[1].displayLines(x0, y0 + cy*3/2);

        } else if (cloudWrapper.getNumOfClouds() == 3) {

            int cy = cliClouds[0].getY();
            cliClouds[0].displayLines(x0, y0);
            cliClouds[1].displayLines(x0, y0 + cy);
            cliClouds[2].displayLines(x0, y0 + 2*cy);

        } else {

            int cx = cliClouds[0].getX();
            int cy = cliClouds[0].getY();
            cliClouds[0].displayLines(x0, y0);
            cliClouds[1].displayLines(x0 + cx, y0 + 2*cy/3);
            cliClouds[2].displayLines(x0, y0 + 4*cy/3);
            cliClouds[3].displayLines(x0 + cx, y0 + 2*cy);

        }

    }

    private void updateCloudArray() {
        cliClouds = new CLICloud[cloudWrapper.getNumOfClouds()];
        for(int i=0; i<cliClouds.length; i++) {
            cliClouds[i] = new CLICloud(cloudWrapper.getCloud(i), i);
        }
    }
}
