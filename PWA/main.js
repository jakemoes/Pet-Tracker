/*  
AUTHORS: Müslüm Atas & Mathias Knoll
DESCRIPTION: The following code detects if the PWA is launched as an app or visited as a website.
SOURCE: https://stackoverflow.com/questions/50543163/can-i-detect-if-my-pwa-is-launched-as-an-app-or-visited-as-a-website
LAST CHANGE: 17.10.2024
*/

// Detects if device is on iOS
const isIos = () => {
    const userAgent = window.navigator.userAgent.toLowerCase();
    return /iphone|ipad|ipod/.test( userAgent );
}
// Detects if device is in standalone mode
const isInStandaloneMode = () => ('standalone' in window.navigator) && (window.navigator.standalone);

// Checks if 'install popup notification' should be displayed
if (isIos() && !isInStandaloneMode()) {
    this.setState({ showInstallMessage: true });
}
