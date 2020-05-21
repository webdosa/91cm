const path = require('path')

module.exports = {
  lintOnSave: false,
  // productionSourceMap: false,
  outputDir: path.resolve(__dirname, '../' + 'src/main/resources/static'),
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:9191',
        // ws: false,
        changeOrigin: true,
        headers: {
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept'
        }
      },
      '/oauth2':{
        target: 'http://localhost:9191',
        changeOrigin: true,
        headers: {
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept'
        }
      },
      '/endpoint':{
        target: 'http://localhost:9191',
        changeOrigin: true,
        headers: {
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept'
        }
      }
    }
  },
  configureWebpack: {
    entry: ['babel-polyfill', './src/main.js']
  }
}
