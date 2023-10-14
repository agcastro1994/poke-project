export const formatName=(name: string)=> {
    if (name[name.length - 2] === '-') {
      if (name.includes('-f')) {
        return name.replace('-f', '\u2640')
      } else {
        return name.replace('-m', '\u2642')
      }
    } else {
      return name.replace('-', '. ')
    }
}