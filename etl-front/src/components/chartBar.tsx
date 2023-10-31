import React, { useEffect, useState } from "react";
import ApexCharts from 'react-apexcharts';
import Loader from "./loader";

interface ChartData {
  [year: string]: number[];
}

interface Props { result: any; }

let chartOptions = {};
let chartSeries: ApexAxisChartSeries | ApexNonAxisChartSeries | undefined = [];


export default function ChartBar({ result }: Props) {
  const [chartData, setChartData] = useState<ApexAxisChartSeries | []>([]);
  const [chartOptions, setChartOptions] = useState({});
  const [chartSeries, setChartSeries] = useState<ApexAxisChartSeries | []>([]);


  // fetchData(result);

  const fetchData = (result: any) => {
    try {
      if (result && result.length >= 4 && result[2] && result[3]) {
        const firstArray = result[2];
        const secondArray = result[3];

        // 1. Fusionner les données
        const data = firstArray.map((firstItem: { numberProd: any; magid: any; }, index: string | number) => {
          const secondItem = secondArray[index];
          const total = firstItem.numberProd;
          const manufacturerX = secondItem.numberProd;
          const percentage = (manufacturerX / total) * 100;
          return {
            magid: firstItem.magid,
            percentage: parseFloat(percentage.toFixed(2)), // Réduire à deux chiffres après la virgule
            total, // Ajouter le nombre total de produits vendus
          };
        });

        // 2. Trier les données en fonction du nombre de produits vendus dans chaque magasin
        const sortedData = data.sort((a: { total: number; }, b: { total: number; }) => b.total - a.total);

        // 3. Créer les séries de données
        const series = [
          {
            name: 'Fabricant X',
            data: sortedData.map((item: { percentage: any; }) => item.percentage),
          },
        ];

        // 4. Configurer les options du graphique
        const options = {
          chart: {
            type: 'radialBar',
            height: 250,
            offsetX: 0,
            foreColor: "#fff",
            toolbar: {
              show: false
            }
          },
          title: {
            text: 'Classement magasin du mois'
          },
          colors: ["#6078ea", "#17ead9", "#f02fc2"],
          stroke: {
            width: 3
          },
          dataLabels: {
            enabled: true
          },
          grid: {
            borderColor: "#40475D"
          },
          plotOptions: {
            radialBar: {
              inverseOrder: false,
              hollow: {
                margin: 5,
                size: "48%",
                background: "transparent"
              },
              track: {
                show: true,
                background: "#40475D",
                strokeWidth: "10%",
                opacity: 1,
                margin: 3 // margin is in pixels
              }
            }
          },
          xaxis: {
            axisTicks: {
              color: "#333"
            },
            axisBorder: {
              color: "#333"
            },
            categories: sortedData.map((item: { magid: any; }) => `Magasin ${item.magid}`),
          },
          fill: {
            type: "gradient",
            gradient: {
              shade: "dark",
              type: "horizontal",
              shadeIntensity: 0.5,
              inverseColors: true,
              opacityFrom: 1,
              opacityTo: 1,
              stops: [0, 100]
            }
          },
          tooltip: {
            theme: "dark",
            yaxis: {
              decimalsInFloat: 2,
              opposite: true,
              labels: {
                offsetX: -10
              }
            },
            y: {
              formatter: (val: any) => `${val}%`,
            },
            legend: {
              position: 'top',
              horizontalAlign: 'left',
              offsetX: 40
            }
          },
        };

        // 4. Mettre à jour les états locaux avec les données
        // chartSeries = series;
        // chartOptions = options;

        // Mettre à jour les états locaux avec les données
        if (series.length > 0) {
          setChartSeries(series);
          setChartOptions(options);
        } else {
          // Réinitialiser l'état local si les données sont vides
          setChartSeries([]);
          setChartOptions({});
        }
      } else {
        // Réinitialiser l'état local si les données ne sont pas valides ou incomplètes
        setChartSeries([]);
        setChartOptions({});
        console.error("Données non valides ou incomplètes.");
      }
    } catch (error) {
      console.error('Erreur lors de la récupération des données :', error);
      // Réinitialiser l'état local en cas d'erreur
      setChartSeries([]);
      setChartOptions({});
    }
  };

  // Utiliser useEffect pour appeler fetchData lorsque les nouvelles données sont disponibles
  useEffect(() => {
    if (result && result.length > 0) {
      fetchData(result);
    } else {
      // Si les données sont égales à 0, réinitialiser l'état local
      setChartSeries([]);
      setChartOptions({});
    }
  }, [result]); // Dépendance à surveiller : result

  return (
    <div>
      {chartSeries.length ?? 0 ? (
        <ApexCharts
          options={chartOptions}
          series={chartSeries}
          type="bar"
          height={350}
        />
      ) : (
        <div>
          Aucune donnée disponible.
        </div>
      )}
    </div>
  )
}