# Bakery

Sample project of a bakery's order, to find the minimum amount of packs to fill an order.
Receives an order with the quantity of each wanted product.
Returns the price, the quantity of packs and the quantity of products in each pack.

<p>
    <img src=https://img.shields.io/badge/author-americodubas-blue.svg>
</p>

## Products

| Name | Code| Packs |
| --- | --- | --- |
| Vegemite Scroll | VS5 | 3 @ $6.99 <br> 5 @ $8.99 |
| Blueberry Muffin | MB11 | 2 @ $9.95 <br> 5 @ $16.95 <br> 8 @ $24.95 |
| Croissant | CF | 3 @ $5.95 <br> 5 @ $9.95 <br> 9 @ $16.99 |

## Sample Input

| Order |
| --- |
| 10 VS5 |
| 14 MB11 |
| 13 CF |

## Sample Output

| Order | Price | Packs |
| --- | --- | --- |
| 10 VS5 | $17.98 | 2 x 5 $8.99 |
| 14 MB11 | $54.80 | 1 x 8 $24.95 <br> 3 x 2 $9.95 |
| 13 CF | $25.85 | 2 x 5 $9.95 <br> 1 x 3 $5.95 |
