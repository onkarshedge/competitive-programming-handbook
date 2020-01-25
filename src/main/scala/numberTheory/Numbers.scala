package numberTheory

object Numbers {
  val isPrime: (Long => Boolean) = number => {
    (2 to Math.sqrt(number).toInt).forall(x => number % x != 0)
  }
  val primes: LazyList[Long] = LazyList.iterate(2L)(i => i + 1).filter(isPrime)

  // LazyList.iterate(2L)(i => i + 1).filter()
  // If I start the lazy list from 2 it will fail as LazyList head is also lazily evaluated.
  // so I added 2 #::
  val primesL: LazyList[Long] = 2 #:: LazyList
    .iterate(3L)(i => i + 2)
    .filter(number =>
      primesL.takeWhile(prime => prime <= Math.sqrt(number)).forall(prime => number % prime != 0)
    )

  val primesR: Stream[Int] = 2 #:: Stream.from(3, step = 2)
    .filter(number =>
      primesR.takeWhile(prime => prime <= Math.sqrt(number)).forall(prime => number % prime != 0)
    )

  def sieveOfEratosthenes: List[Int] = {
    val limit = 1e6.toInt
    val isComposite: Array[Boolean] = new Array(limit)
    isComposite(2) = false
    isComposite(0) = true
    isComposite(1) = true
    for (i <- 2 to Math.sqrt(limit).toInt) {
      if (!isComposite(i)) {
        for (j <- i * i until limit by i) {
          isComposite(j) = true
        }
      }
    }
    isComposite.zipWithIndex.filter(t => !t._1).map(_._2).toList
  }

  def main(args: Array[String]): Unit = {
    primesL.slice(9999, 10000).foreach(println(_))
    sieveOfEratosthenes.slice(9999, 10000).foreach(println(_))
  }
}
