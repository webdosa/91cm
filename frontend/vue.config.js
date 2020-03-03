const path = require('path')

module.exports = {
  lintOnSave: false,
  outputDir: path.resolve(__dirname, "../"+"src/main/resources/static"),
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:9191',
        ws: true,
        changeOrigin: true
      },
    }
  }
}
