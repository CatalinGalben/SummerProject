import {CompanyTypes, ETFTypes, MetricYear, Mockmodels} from './mockmodels';

export const RECORDS: Mockmodels[] = [
  { name: 'Mr. Nice', pricePaid: 1000, noShares:100 },
  { name: 'Narco', pricePaid: 1300, noShares:180 },
  { name: 'Bombasto', pricePaid: 3400, noShares:500 },
  { name: 'Celeritas', pricePaid: 500, noShares:70 },
  { name: 'Magneta', pricePaid: 1030, noShares:190 },
  { name: 'RubberMan', pricePaid: 6760, noShares:670 },
  { name: 'Dynama', pricePaid: 2130, noShares:170 },
  { name: 'Dr IQ', pricePaid: 1230, noShares:500 },
  { name: 'Magma', pricePaid: 8900, noShares:600 },
  { name: 'Tornado', pricePaid: 10000, noShares:1000 },

  { name: 'DC', pricePaid: 1000, noShares:100 },
  { name: 'Nirvana', pricePaid: 1300, noShares:170 },
  { name: 'Bamboo', pricePaid: 3200, noShares:400 },
  { name: 'Cele', pricePaid: 544, noShares:60 },
  { name: 'Felicita', pricePaid: 1210, noShares:170 },
  { name: 'Troi', pricePaid: 5560, noShares:570 },
  { name: 'Feri', pricePaid: 1130, noShares:170 },
  { name: 'Dr Q', pricePaid: 3430, noShares:100 },
  { name: 'Nanda', pricePaid: 1900, noShares:200 }
];

export const COMPANYTYPES: CompanyTypes[] = [
  { number: 1, type: 'Shares' },
  { number: 2, type: 'Trust' },
  { number: 3, type: 'ETF' }
];

export const ETFTYPES: ETFTypes[] = [
  { number: 1, type: 'Index Fund'},
  { number: 2, type: 'Real ETF'},
  { number: 3, type: 'Synthetic ETF'}
];

export const YEARSMETRICS: MetricYear[] = [
  {year: 2018},
  {year: 2017},
  {year: 2016},
  {year: 2015},
  {year: 2014},
  {year: 2013}
];

export const LABELS: string[] = [
  "value1", "value2", "value3"
];
