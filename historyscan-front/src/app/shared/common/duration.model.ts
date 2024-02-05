export class Duration {
  private startTime!: Date;
  private endTime!: Date;
  private duration: number = 0;

  constructor() {

  }

  start() {
    this.startTime = new Date();
  }

  end() {
    this.endTime = new Date();
    this.duration = this.endTime.getTime() - this.startTime.getTime();
  }

  format(): string {
    if (this.duration) {
      const milliseconds = this.duration % 1000;
      const seconds = Math.floor((this.duration / 1000) % 60);
      const minutes = Math.floor((this.duration / (1000 * 60)) % 60);
      const hours = Math.floor(this.duration / (1000 * 60 * 60));
      const hoursDisplay = hours > 0 ? `${hours} hour${hours > 1 ? 's' : ''} ` : '';
      const minutesDisplay = minutes > 0 ? `${minutes} minute${minutes > 1 ? 's' : ''} ` : '';
      const secondsDisplay = seconds > 0 ? `${seconds} second${seconds > 1 ? 's' : ''} ` : '';
      const millisecondsDisplay = milliseconds > 0 ? `${milliseconds} millisecond${milliseconds > 1 ? 's' : ''}` : '';
      return hoursDisplay + minutesDisplay + secondsDisplay + millisecondsDisplay;
    }
    return '';
  }
}
