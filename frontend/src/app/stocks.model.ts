export interface Quotes {

    symbol: string
    lastPrice: number
    openPrice: number
    closePrice: number
    highPrice: number
    lowPrice: number
    netChange: number
    totalVolume: number
    FiftyTwoWkHigh: number
    FiftyTwoWkLow: number

}

export interface Journal {

    uuid: string
    symbol: string
    quantity: number
    position: string //long or short
    tradeType: string //day, swing, position
    entryPrice: number
    exitPrice: number
    entryDate: Date //use date or number?
    exitDate: Date
    pnl: number
    comments: string
    imageUrl: string
    
}

export interface Day {
    
    day_id: string
    pnl: number
    date: Date
}

export interface User {

    email:string
    name: string
    password: string
}

