package br.com.coodesh.sfn.service.mapper;

public interface Mapper<A, B> {
	B map(A input);
}
