package io.sch.historyscan.fake;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.RevisionStats;

public class RevisionsStatsFake {
    public static final RevisionStats singleFile1Stats = new RevisionStats(22, 20, 440);
    public static final RevisionStats singleFile2Stats = new RevisionStats(22, 20, 440);

    public static final RevisionStats file2Stats = new RevisionStats(310, 1500, 465000);
    public static final RevisionStats file3Stats = new RevisionStats(190, 1250, 237500);
    public static final RevisionStats file1Stats = new RevisionStats(22, 20, 440);

    public static final RevisionStats file5Stats = new RevisionStats(222, 523, 116106);
    public static final RevisionStats file4Stats = new RevisionStats(135, 150, 20250);
    public static final RevisionStats file6Stats = new RevisionStats(22, 20, 440);
}
