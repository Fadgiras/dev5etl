// Utilisation de l'API fetch pour charger le fichier JSON
fetch('/build/json/produitsLimite.json')
  .then(response => response.json()) // Convertir la réponse en JSON
  .then(data => {
    // Les données JSON sont maintenant disponibles dans la variable 'data'
    console.log(data); // Affichez les données pour vérification

    // FABID de référence
    const fabIdToFind = 541;

    // Rechercher l'objet avec le FABID correspondant
    const foundObject = data.find(item => item.FABID === fabIdToFind);

    if (foundObject) {
      // Adapter le format des dates et extraire les valeurs de vente
      const salesData = data
        .filter(item => item.FABID === fabIdToFind) // Filtrer par FABID
        .map(item => {
          // Adapter le format de la date (YYYYMMDD à YYYY-MM-DD)
          const formattedDate = `${item.DATE.substring(0, 4)}-${item.DATE.substring(4, 6)}-${item.DATE.substring(6, 8)}`;

          // Extraire les valeurs de vente ou tout autre champ que vous souhaitez afficher
          const salesValue = item.VENTE; // Assurez-vous de remplacer "VENTE" par le champ approprié dans votre objet

          return { date: formattedDate, sales: salesValue };
        });

      // chart 1
      var ctx = document.getElementById("chart-bars").getContext("2d");

      new Chart(ctx, {
        type: "bar",
        data: {
          labels: salesData.map(item => item.date), // Utilisez les dates formatées
          datasets: [
            {
              label: "Sales",
              tension: 0.4,
              borderWidth: 0,
              borderRadius: 4,
              borderSkipped: false,
              backgroundColor: "#fff",
              data: salesData.map(item => item.sales), // Utilisez les valeurs de vente
              maxBarThickness: 6,
            },
          ],
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: {
            legend: {
              display: false,
            },
          },
          interaction: {
            intersect: false,
            mode: "index",
          },
          scales: {
            y: {
              grid: {
                drawBorder: false,
                display: false,
                drawOnChartArea: false,
                drawTicks: false,
              },
              ticks: {
                suggestedMin: 0,
                suggestedMax: 600,
                beginAtZero: true,
                padding: 15,
                font: {
                  size: 14,
                  family: "Open Sans",
                  style: "normal",
                  lineHeight: 2,
                },
                color: "#fff",
              },
            },
            x: {
              grid: {
                drawBorder: false,
                display: false,
                drawOnChartArea: false,
                drawTicks: false,
              },
              ticks: {
                display: false,
              },
            },
          },
        },
      });
    } else {
      console.log("Aucun objet trouvé avec FABID =", fabIdToFind);
    }
  })
  .catch(error => {
    console.error('Erreur lors du chargement des données JSON :', error);
  });

