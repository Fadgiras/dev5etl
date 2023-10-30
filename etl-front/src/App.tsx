import React, { useEffect, useState } from 'react';
import logo from './logo.svg';

import './App.css';
import ApexCharts from 'react-apexcharts';
import Header from './components/Header';
import Footer from './components/Footer';
import Sidebar from './components/Sidebar';
import ChartBar from './components/chartBar';
import ChartArea from './components/chartArea';
import Loader from './components/loader';

interface Props { }

interface ChartData {
  [year: string]: number[];
}

const App: React.FC<Props> = (props) => {

  // const [data, setData] = useState<any>([]);
  // const [products, setProducts] = useState<Product[]>([]);
  const [chartData, setChartData] = useState<ChartData>({});
  const [chartOptions, setChartOptions] = useState<any>();
  const [chartResult, setChartResult] = useState<any>([]);
  const [catList, setCatList] = useState<any>();
  const [fabList, setFabList] = useState<any>([]);
  const [months, setMonths] = useState<any>([]);
  const [selectedCat, setSelectedCat] = useState<string>('1');
  const [selectedMonth, setSelectedMonth] = useState<string>('May');
  const [selectedYear, setSelectedYear] = useState<string>('2022');

  // Options initiales pour le graphique ApexCharts
  const initialChartOptions = {
    options: {
      xaxis: {
        type: "string",
      },
    },
    series: [],
  };

  interface ChartOptions {
    options: {
      xaxis: {
        type: string;
      };
    };
    series: {
      name: string;
      data: number[][];
    }[];
  }

  const fetchCatIds = async () => {
    const url = 'http://127.0.0.1:8080/pointvente/list/catid'
    const result = await fetch(url).then(response => response.json())
    console.log("catList")
    console.log(result)
    setCatList(result)
  }

  const monthsMap = {
    "Jan": ["01-01", "01-31"],
    "Feb": ["02-01", "02-28"],
    "Mar": ["03-01", "03-31"],
    "Apr": ["04-01", "04-30"],
    "May": ["05-01", "05-31"],
    "Jun": ["06-01", "06-30"],
    "Jul": ["07-01", "07-31"],
    "Aug": ["08-01", "08-31"],
    "Sep": ["09-01", "09-30"],
    "Oct": ["10-01", "10-31"],
    "Nov": ["11-01", "11-30"],
    "Dec": ["12-01", "12-31"],
  };


  const fetchData = async () => {
    //----------------------------------------------GET URL------------------------------------------------------//
    try {
      const url =
        "http://127.0.0.1:8080/pointvente/health/fab/109/cat/" + selectedCat + "/date/" + selectedYear + "-" + monthsMap[selectedMonth as keyof typeof monthsMap][0] + "/" + selectedYear + "-" + monthsMap[selectedMonth as keyof typeof monthsMap][1]

      const result = await fetch(url).then(response => response.json())
      console.log(result)
      setChartResult(result)
      //
      //
      //
      // ------------------------------------------------SET DATA 1------------------------------------------------//
      const months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]
      console.log(months)

      const seriesMonths22 = months.map(
        (val, i) =>
          `01-${(i + 1).toLocaleString('en-US', {
            minimumIntegerDigits: 2,
            useGrouping: false,
          })}-2022`
      ).map(val => result[0]["2022"][val] ?? 0)
      console.log(seriesMonths22)

      const seriesMonths23 = months.map(
        (val, i) => `01-${(i + 1).toLocaleString('en-US', {
          minimumIntegerDigits: 2,
          useGrouping: false,
        })}-2023`
      ).map(val => result[1]["2023"][val] ?? 0)
      console.log(seriesMonths23)

      const datard = {
        series: [{
          name: '2022',
          data: seriesMonths22
        }, {
          name: '2023',
          data: seriesMonths23
        }],
        options: {
          chart: {
            height: 350,
            type: 'bar',
          },
          plotOptions: {
            bar: {
              borderRadius: 10,
              dataLabels: {
                position: 'top', // top, center, bottom
              },
            }
          },
          // dataLabels: {
          //   enabled: true,
          //   formatter: function (val: any) {
          //     return val + "%";
          //   },
          //   offsetY: -20,
          //   style: {
          //     fontSize: '12px',
          //     colors: ["#304758"]
          //   }
          // },
          xaxis: {
            categories: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
            position: 'top',
            axisBorder: {
              show: false
            },
            axisTicks: {
              show: false
            },
            crosshairs: {
              fill: {
                type: 'gradient',
                gradient: {
                  colorFrom: '#D8E3F0',
                  colorTo: '#BED1E6',
                  stops: [0, 100],
                  opacityFrom: 0.4,
                  opacityTo: 0.5,
                }
              }
            },
            tooltip: {
              enabled: true,
            }
          },
        },
        title: {
          text: 'Monthly Inflation in Argentina, 2002',
          floating: true,
          offsetY: 330,
          align: 'center',
          style: {
            color: '#444'
          }
        }
      }
      // Mettez à jour les états locaux avec les données (STYLE INITIAL)
      setChartOptions(datard.options)
      setChartSeries(datard.series)
      // ------------------------------------------------------------------------------------------------------//
      //
      //
      //
      // ------------------------------------------------SET DATA 2------------------------------------------------//
      const years = ["2022", "2023"]

      const seriesYears22 = years.map(
        val => result[1]["2022"][val] ?? 0)
      console.log(seriesYears22)

      const seriesYears23 = years.map(
        val => result[2]["2023"][val] ?? 0)
      console.log(seriesYears23)
    } catch (error) {
      console.error('Erreur lors de la récupération des données :', error);
      // Réinitialiser l'état local en cas d'erreur
      setChartSeries([]);
      setChartOptions({});
    }
  }
  useEffect(() => {
    fetchCatIds()
    fetchData()
  }, [selectedCat, selectedMonth, selectedYear]
  );

  const handleCatChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedCat(event.target.value);
    fetchData()
  };

  const handleMonthChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedMonth(event.target.value);
    fetchData()
  };

  const handleYearChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    // setSelectedYear(event.target.value);
    // fetchData()
    const newYear = event.target.value;
    setSelectedYear(newYear);
  };


  const [chartSeries, setChartSeries] = useState<any>([]);

  // // ------------------------------------------------WAIT DATA------------------------------------------------//
  // if (!chartOptions || !chartSeries) {
  //   <Loader />
  // }
  // // ------------------------------------------------------------------------------------------------------//

  return (
    <div className="app-container">
      <Header />
      <div className="content-container">
        <Sidebar />
        <main>
          <h1>
            <i className="material-icons">space_dashboard</i> Dashboard
          </h1>
          <div>
            {/* Select for category */}
            <select onChange={handleCatChange} value={selectedCat}>
              <option value="">Catégorie</option>
              {catList && catList.map((id: number) => (
                <option key={id} value={id}>
                  Catégorie {id}
                </option>
              ))}
            </select>

            {/* Select for month */}
            <select value={selectedMonth} onChange={handleMonthChange}>
              <option value="">Mois</option>
              {Object.keys(monthsMap).map((month) => (
                <option key={month} value={month}>
                  {month}
                </option>
              ))}
            </select>
            {/* Select for year */}
            <select value={selectedYear} onChange={handleYearChange}>
              <option value={"2022"}>
                2022
              </option>
              <option value={"2023"}>
                2023
              </option>
            </select>
          </div>

          {/* -------------------------ApexChart Get Data Graphique 1------------------------- */}
          <h2>Parts des ventes par mois</h2>
          <ChartArea result={chartResult} />
          {/* -------------------------ApexChart Get Data Graphique 2------------------------- */}          <h2>Classement magasin du mois</h2>
          <ChartBar result={chartResult} />


        </main>
      </div>
      <Footer />
    </div>
  )

}

export default App;