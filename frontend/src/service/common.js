class CommonClass{
    replacemsg(originContent) {
        let array = originContent.split("\n")
        let content = ''
        for (let i in array) {
          content += '<p>' + array[i] + '</p>'
        }
        return content.replace(/ /gi, '&nbsp;')
      }
}
export default new CommonClass()