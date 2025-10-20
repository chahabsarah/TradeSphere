import { AfterViewInit, Component, ElementRef, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ProductListComponent implements OnInit, AfterViewInit {
  @ViewChild('widgetContainer', { static: false }) widgetContainer!: ElementRef;

  constructor() { }

  ngAfterViewInit() {
    this.addTradingViewTickerTapeWidget();

    // Inject TradingView Market Quotes widget
    this.addTradingViewMarketQuotesWidget();

    // Inject TradingView Advanced Chart widget
    this.addTradingViewAdvancedChartWidget();
  }

  private addTradingViewTickerTapeWidget() {
    if (this.widgetContainer) {
      const script = document.createElement('script');
      script.type = 'text/javascript';
      script.src = 'https://s3.tradingview.com/external-embedding/embed-widget-ticker-tape.js';
      script.async = true;
      script.innerHTML = JSON.stringify({
        "symbols": [
          { "proName": "FOREXCOM:SPXUSD", "title": "S&P 500 Index" },
          { "proName": "FOREXCOM:NSXUSD", "title": "US 100 Cash CFD" },
          { "proName": "FX_IDC:EURUSD", "title": "EUR to USD" },
          { "proName": "BITSTAMP:BTCUSD", "title": "Bitcoin" },
          { "proName": "BITSTAMP:ETHUSD", "title": "Ethereum" }
        ],
        "showSymbolLogo": true,
        "isTransparent": false,
        "displayMode": "adaptive",
        "colorTheme": "light",
        "locale": "en"
      });

      this.widgetContainer.nativeElement.appendChild(script);
    } else {
      console.error('Widget container element not found!');
    }
  }
  private addTradingViewMarketQuotesWidget() {
    const container = document.querySelector('.tradingview-market-quotes-container');
    if (container) {
      const script = document.createElement('script');
      script.type = 'text/javascript';
      script.src = 'https://s3.tradingview.com/external-embedding/embed-widget-market-quotes.js';
      script.async = true;
      script.innerHTML = JSON.stringify({
        "title": "Stocks",
        "width": "100%",
        "height": "100%",
        "locale": "en",
        "showSymbolLogo": true,
        "symbolsGroups": [
          {
            "name": "Financial",
            "symbols": [
              { "name": "NYSE:JPM", "displayName": "JPMorgan Chase" },
              { "name": "NYSE:WFC", "displayName": "Wells Fargo Co New" },
              { "name": "NYSE:BAC", "displayName": "Bank Amer Corp" },
              { "name": "NYSE:HSBC", "displayName": "Hsbc Hldgs Plc" },
              { "name": "NYSE:C", "displayName": "Citigroup Inc" },
              { "name": "NYSE:MA", "displayName": "Mastercard Incorporated" }
            ]
          },
          {
            "name": "Technology",
            "symbols": [
              { "name": "NASDAQ:AAPL", "displayName": "Apple" },
              { "name": "NASDAQ:GOOGL", "displayName": "Alphabet" },
              { "name": "NASDAQ:MSFT", "displayName": "Microsoft" },
              { "name": "NASDAQ:FB", "displayName": "Meta Platforms" },
              { "name": "NYSE:ORCL", "displayName": "Oracle Corp" },
              { "name": "NASDAQ:INTC", "displayName": "Intel Corp" }
            ]
          },
          {
            "name": "Services",
            "symbols": [
              { "name": "NASDAQ:AMZN", "displayName": "Amazon" },
              { "name": "NYSE:BABA", "displayName": "Alibaba Group Hldg Ltd" },
              { "name": "NYSE:T", "displayName": "At&t Inc" },
              { "name": "NYSE:WMT", "displayName": "Walmart" },
              { "name": "NYSE:V", "displayName": "Visa" }
            ]
          }
        ],
        "colorTheme": "light"
      });
  
      container.appendChild(script); // Append the script to the container
    } else {
      console.error('Market Quotes container not found!');
    }
  }

  private addTradingViewAdvancedChartWidget() {
    const container = document.querySelector('.tradingview-advanced-chart-container');
    if (container) {
      const script = document.createElement('script');
      script.type = 'text/javascript';
      script.src = 'https://s3.tradingview.com/external-embedding/embed-widget-advanced-chart.js';
      script.async = true;
      script.innerHTML = JSON.stringify({
        "autosize": true,
        "symbol": "NASDAQ:AAPL",
        "interval": "D",
        "timezone": "Etc/UTC",
        "theme": "light",
        "style": "1",
        "locale": "en",
        "withdateranges": true,
        "hide_side_toolbar": false,
        "allow_symbol_change": true,
        "details": true,
        "hotlist": true,
        "calendar": false,
        "show_popup_button": true,
        "popup_width": "1000",
        "popup_height": "650",
        "support_host": "https://www.tradingview.com"
      });

      container.appendChild(script); // Append the script to the container
    } else {
      console.error('Advanced Chart container not found!');
    }
  }
  
  ngOnInit() { }
}
