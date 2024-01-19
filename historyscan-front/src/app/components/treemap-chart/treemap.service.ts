import {Injectable} from "@angular/core";
import {EChartsOption, SeriesOption} from "echarts";
import {
  CodebaseClocRevisionsFileTree
} from "../../pages/analysis/cloc-revisions/codebase-cloc-revisions-file-tree.model";

@Injectable({providedIn: 'root'})
export class TreemapService {
  defaultChartOptions(): EChartsOption {
    return {
      title: {
        text: 'Loading...',
        textStyle: {
          color: '#9f9f9f'
        },
      },
    };
  }

  public searchItemInTreemap(seriesItem: SeriesOption, targetItem: string) {
    if (seriesItem && 'type' in seriesItem && seriesItem.type === 'treemap' && 'data' in seriesItem) {
      const data = seriesItem.data;
      return this.findInSeries(data![0], targetItem);
    }
    return null;
  }

  toTreeMapOptions(node: CodebaseClocRevisionsFileTree, title: string, description: string): EChartsOption {
    const data = this.createTreeStructure(node);
    const maxScore = this.findMaxScore(node);
    return {
      title: {
        text: title,
        textStyle: {
          color: '#9f9f9f'
        },
        subtext: description,
        subtextStyle: {
          color: '#9f9f9f'
        }
      },
      autoResize: true,
      animation: true,
      darkMode: true,
      tooltip: {
        trigger: 'item',
        position: 'top',
        borderWidth: 2,
        borderColor: '#171d31',
        backgroundColor: '#d0d6ee',
        textStyle: {
          color: '#000',
          fontSize: 10,
          fontWeight: 'bold',
          fontFamily: 'monospace',
        },
        formatter: (params: any) => {
          const name = params.name;
          const path = params.data.path;
          const score = this.formatScore(params.value);
          return `Name: ${name}<br>Path: ${path}<br>Score: ${score}`;
        }
      },
      visualMap: {
        type: 'continuous',
        min: 0,
        max: maxScore,
        inRange: {
          color: [
            '#2f6e7c',
            '#8b63d3',
            '#a1597f',

            '#c95239',
            '#c74a21',
            '#b13333',
          ]
        },
      },
      series: [
        {
          type: 'treemap',
          itemStyle: {
            color: '#1c1e2f',
            borderColor: '#fff',
          },
          label: {
            show: true,
            color: '#000',
            formatter: (params: any) => params.data.isFile ? `${params.name}\n ${params.value}` : params.name,
          },
          breadcrumb: {
            top: 1,
          },
          height: '100%',
          upperLabel: {
            color: '#bdb8b8',
            backgroundColor: '#1c1e2f',
            show: true,
            height: 20,
            fontSize: 10,
            lineHeight: 10,
          },
          visibleMin: 2,
          levels: this.levelOption(),
          data,
          selectedMode: 'single',
        },

      ] as SeriesOption[],
    };
  }

  private findMaxScore(node: CodebaseClocRevisionsFileTree): number {
    let maxScore = node.isFile ? node.score : 0;
    if (node.children && node.children.length > 0) {
      for (const child of node.children) {
        const childMax = this.findMaxScore(child);
        if (childMax > maxScore) {
          maxScore = childMax;
        }
      }
    }
    return maxScore;
  }

  private levelOption() {
    return [
      {
        emphasis: {
          itemStyle: {
            borderColor: '#ddd'
          }
        },
        upperLabel: {
          show: true
        },
        borderWidth: 3,
        borderColorSaturation: 0.6,
        mappingBy: 'value',
      }
    ];
  }

  private formatScore(score: number): string {
    return score.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ");
  }

  private createTreeStructure(revisions: CodebaseClocRevisionsFileTree): any[] {
    const nodesMap: any = {};
    this.createNodes(revisions, nodesMap, 1);
    const root = {name: 'root', children: []};
    this.assignChildren(revisions, nodesMap, root);
    return root.children;
  }

  private createNodes(revisions: CodebaseClocRevisionsFileTree, nodesMap: any, index: number) {
    if ((revisions.isFile || !!revisions.children.length)) {
      nodesMap[revisions.path] = {
        name: revisions.name,
        value: revisions.score,
        path: revisions.path,
        isFile: revisions.isFile,
        dataIndex: index,
        children: []
      };
      index++;
    }
    revisions.children.forEach(child => {
      if (child.isFile || !!child.children.length) {
        index = this.createNodes(child, nodesMap, index);
      }
    });
    return index;
  }

  private assignChildren(revisions: CodebaseClocRevisionsFileTree, nodesMap: any, root: any) {
    if (revisions.parentPath !== undefined && nodesMap[revisions.parentPath]) {
      let parentNode = nodesMap[revisions.parentPath];
      let node = nodesMap[revisions.path];

      if ((node.children.length === 1 && !node.children[0].isFile) || node.path === 'pathToSkip') {
        node = node.children[0];
      }

      parentNode.children.push(node);
    } else if (revisions.path === '/') {
      const node = nodesMap[revisions.path];
      root.children.push(node);
    }
    revisions.children.forEach(child => this.assignChildren(child, nodesMap, root));
  }

  findInSeries(node: any, targetPath: string): any {
    if (node.path.includes(targetPath)) {
      return node;
    }
    if (node.children) {
      for (let child of node.children) {
        const result = this.findInSeries(child, targetPath);
        if (result) {
          return result;
        }
      }
    }
    return null;
  }
}
