var sample: int = 5;
var sample2: int = 10;
print(sample+sample2);

var x: int = 10;
var y: float = 50.5;

if (x < 15) {
    print(1500);
} else {
    print(2000);
}


func add(a: int, b: int): int {
    return a + b;
}


var result: int = add(x, 5);
print(result);

func mult(a: int, b: int): int {
    return a * b;
}


var ans: int = mult(y, 10);
print(ans);