const path = require('path')

module.exports = {
  lintOnSave: false,
  productionSourceMap: false,
  outputDir: path.resolve(__dirname, '../' + 'src/main/resources/static'),
  devServer: {
    // host: 'localhost'
    // proxy: {
    //   '/': {
    //     target: 'http://localhost:9191',
    //     ws: false,
    //     changeOrigin: true,
    //     headers: {
    //       'Access-Control-Allow-Origin': '*',
    //       'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept'
    //     }
    //   }
    // }
  },
  configureWebpack: {
    entry: ['babel-polyfill', './src/main.js']
  }
}
