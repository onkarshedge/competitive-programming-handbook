from math import sqrt, ceil
from itertools import *
import sys


def sieve_of_eratosthenes(n=100000):
    is_prime = [False, False] + [True] * n
    for prime in range(2, ceil(sqrt(n))):
        for multiple in range(prime * prime, n + 1, prime):
            is_prime[multiple] = False

    def generator():
        for j in range(n + 1):
            if is_prime[j]:
                yield j

    return generator()


def generate_prime_numbers():
    """
    This is a generator function to generate primes on demand. It is readable and concise but not performance optimal.
    """
    n = 2
    yield n
    while True:
        is_not_prime = True
        while is_not_prime:
            n = n + 1
            primes_less_than_sqrt_n = takewhile(lambda x: x <= sqrt(n), generate_prime_numbers())
            is_not_prime = any(n % x == 0 for x in primes_less_than_sqrt_n)
        yield n


def generate_prime_numbers_2():
    """
    This is a generator function to generate primes on demand. It is readable and concise but not performance optimal.
    """
    n = 2
    yield n
    while True:
        is_not_prime = True
        while is_not_prime:
            n = n + 1
            numbers_less_than_sqrt_n = range(2, int(ceil(sqrt(n))) + 1)
            is_not_prime = any(n % x == 0 for x in numbers_less_than_sqrt_n)
        yield n


def nth(iterable, n, default=None):
    """Returns the nth item or a default value"""
    return next(islice(iterable, n - 1, None), default)


if __name__ == "__main__":
    gen = generate_prime_numbers()
    # seq, memoized_seq = tee(gen)
    print("first 10 primes are")
    first_10_primes = islice(gen, 10)
    for p in first_10_primes:
        print(p)

    print(f'sys.maxsize is {sys.maxsize}')

    # this statement took avg 21.36 seconds
    print(f'10000th prime is {nth(generate_prime_numbers(), 10 ** 4, default=-1)}')

    # this statement took avg 0.346 seconds
    print(f'10000th prime is {nth(generate_prime_numbers_2(), 10 ** 4, default=-1)}')

    # this statement took avg 6.85 seconds with n = 1e7
    # and when n was 1e6 it just took 0.43s
    gen = sieve_of_eratosthenes(n=1000000)
    print(f'10000th prime is {nth(gen, 10 ** 4, default=-1)}')

    # import time
    # start_time = time.time()
    # print("--- %s seconds ---" % (time.time() - start_time))
