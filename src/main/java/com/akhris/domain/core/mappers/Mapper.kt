package com.akhris.domain.core.mappers


/**
 * Mapper class that maps A to B using [IMapper] objects.
 */
class Mapper<A, B>(
    private val toMapper: IMapper<A, B>,
    private val fromMapper: IMapper<B, A>
) {

    /**
     * Constructor that accepts lambdas instead of interface objects.
     */
    constructor(mapTo: (A) -> B, mapFrom: (B) -> A) : this(
        toMapper = object : IMapper<A, B> {
            override fun map(input: A): B = mapTo(input)
        },
        fromMapper = object : IMapper<B, A> {
            override fun map(input: B): A = mapFrom(input)
        }
    )

    /**
     * function to map A to B
     */
    fun mapTo(input: A): B {
        return toMapper.map(input)
    }

    /**
     * function to map B to A
     */
    fun mapFrom(input: B): A {
        return fromMapper.map(input)
    }

    /**
     * function to map iterables A to B
     */
    fun mapTo(input: Iterable<A>): Iterable<B> = input.map {
        toMapper.map(it)
    }

    /**
     * function to map iterables B to A
     */
    fun mapFrom(input: Iterable<B>): Iterable<A> = input.map {
        fromMapper.map(it)
    }
}


/**
 * Base mapper interface.
 * Maps input [I] to output [O]
 */
interface IMapper<I, O> {
    fun map(input: I): O
}
