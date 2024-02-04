import {TreemapChartService} from "./treemap-chart.service";

describe('TreemapService', () => {
  const expectedItem = {
    path: 'historyscan-back/src/main/java/io/sch/historyscan/domain/contexts/analysis/clocrevisions/filesystem/ActualFileSystemTree.java',
    dataIndex: 196,
    children: []
  };
  test.each([
    ['ActualFileSystemTree.'],
    ['ActualFileSystemTree.java'],
    ['ActualFileSystemTree.ja'],
    ['FileSystemTree.ja'],
    ['lFileSystemTree.java'],
    ['historyscan/domain/contexts/analysis/clocrevisions/filesystem/ActualFileSystemTree.java'],
    ['historyscan/domain/contexts/analysis/clocrevisions/filesystem/ActualFileSystemTree.ja'],
    ['historyscan/domain/contexts/analysis/clocrevisions/filesystem/ActualFileSystemTree.'],
    ['historyscan/domain/contexts/analysis/clocrevisions/filesystem/ActualFileSystemTree'],
  ])('should find a file or folder from node', (targetPath: string) => {
    const foundItem = new TreemapChartService().findInSeries({
      path: 'historyscan-back/src/main/java/io/sch/historyscan/domain/contexts/analysis/clocrevisions/filesystem',
      dataIndex: 195,
      children: [expectedItem]
    }, targetPath);
    expect(foundItem.dataIndex).toEqual(expectedItem.dataIndex);
    expect(foundItem.path).toEqual(expectedItem.path);

  });
});
