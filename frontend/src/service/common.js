class CommonClass {
  replacemsg(originContent) {
    let array = originContent.split("\n")
    let content = ''
    for (let i in array) {
      content += '<p>' + array[i] + '</p>'
    }
    return content.replace(/ /gi, '&nbsp;')
  }

  replacemsgForPreview(originContent) {
    let array = originContent.split("</p><p>")
    let content = ''
    for (let i in array) {
      content += array[i] + ' '
    }
    return content
  }

  byteCount(s, b, i, c) {
    for (b = i = 0; c = s.charCodeAt(i++); b += c >> 11 ? 3 : c >> 4 ? (c >> 7 ? 2 : 1) : 2) ;
    return b
  }

  byteLimit(length,type) {
    console.log('type: '+type)
    if (length > 30000) {
      alert('최대 30000byte까지 입력이 가능합니다.')
      return false
    } else {
      return true
    }
  }

}

export default new CommonClass()
