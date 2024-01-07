import {Injectable} from "@angular/core";
import {EChartsOption} from "echarts";
import {
  CodebaseClocRevisionsFileTree
} from "../../pages/analysis/cloc-revisions/codebase-cloc-revisions-file-tree.model";

@Injectable({providedIn: 'root'})
export class TreemapService {
  defaultChartOptions(): EChartsOption {
    return {
      title: {
        text: 'Loading...',
      },
    };
  }

  toTreeMapOptions(node: CodebaseClocRevisionsFileTree, title: string, description: string): EChartsOption {
    const data = this.createTreeStructure(node);
    console.log(data);
    return {
      title: {
        text: title,
        subtext: description,
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
          // params contient toutes les informations sur l'élément survolé
          const name = params.name; // ou params.data.name
          const path = params.data.path; // ou params.data.value
          const score = this.formatScore(params.value); // Accès à la propriété extraInfo

          return `Name: ${name}<br>Path: ${path}<br>Score: ${score}`;
        }
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
            formatter: '{b}'
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
          visibleMin: 300,
          levels: this.levelOption(),
          data,
        },
      ],
    };
  }

 levelOption() {
    return [
      {
        itemStyle: {
          borderColor: '#777',
          borderWidth: 0,
          gapWidth: 1
        },
        upperLabel: {
          show: false
        }
      },
      {
        itemStyle: {
          borderColor: '#555',
          borderWidth: 5,
          gapWidth: 1
        },
        emphasis: {
          itemStyle: {
            borderColor: '#ddd'
          }
        }
      },
      {
        colorSaturation: [0.35, 0.5],
        itemStyle: {
          borderWidth: 5,
          gapWidth: 1,
          borderColorSaturation: 0.6
        }
      }
    ];
  }

  private formatScore(score: number): string {
    return score.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ");
  }

  private createTreeStructure(revisions: CodebaseClocRevisionsFileTree): any[] {
    const nodesMap: any = {};
    this.createNodes(revisions, nodesMap);

    const root = {name: 'root', children: []};
    this.assignChildren(revisions, nodesMap, root);

    return root.children;
  }

  private createNodes(revisions: CodebaseClocRevisionsFileTree, nodesMap: any) {
    if (revisions.path !== undefined && revisions.score !== undefined) {
      nodesMap[revisions.path] = {
        name: revisions.name,
        value: revisions.score,
        path: revisions.path,
        children: []
      };
    }

    revisions.children.forEach(child => this.createNodes(child, nodesMap));
  }

  private assignChildren(revisions: CodebaseClocRevisionsFileTree, nodesMap: any, root: any) {
    if (revisions.parentPath !== undefined && nodesMap[revisions.parentPath]) {
      let parentNode = nodesMap[revisions.parentPath];
      let node = nodesMap[revisions.path];

      // Skip nodes that have only one child and that child is not a file, or nodes that have a certain path
      if ((node.children.length === 1 && !node.children[0].isFile) || node.path === 'pathToSkip') {
        node = node.children[0];
      }

      parentNode.children.push(node);
    } else if (revisions.path === '/') {
      const node = nodesMap[revisions.path];
      root.children.push(node);
    } else {
      // throw new Error(`No parent found for ${revisions.path}`);
    }

    revisions.children.forEach(child => this.assignChildren(child, nodesMap, root));
  }
}
