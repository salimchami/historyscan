import {Component, Input} from "@angular/core";

@Component({
  selector: 'app-network-chart',
  templateUrl: './network-chart.component.html',
  styleUrls: ['./network-chart.component.scss'],
})
export class NetworkChartComponent {
  @Input() chartTitle!: string;
}
