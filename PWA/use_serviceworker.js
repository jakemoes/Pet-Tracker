/*  
AUTHORS: Müslüm Atas & Mathias Knoll
DESCRIPTION: Registration of the Service Worker. If the service worker API is supported in the browser, it is registered using the 
             ServiceWorkerContainer.register() method.
LAST CHANGE: 17.10.2024
*/


if ("serviceWorker" in navigator) {
    window.addEventListener('load', function() {
        navigator.serviceWorker
            .register("serviceworker.js")
            .then(serviceWorker => {
                console.log("Service Worker Pet Tracker registered: ", serviceWorker);
            })
            .catch(error => {
                console.error("Error registering the Service Worker  Pet Tracker: ", error);
            });
    });
}

