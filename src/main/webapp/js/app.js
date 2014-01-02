angular.module("sprang.services", ["ngResource"]).
    factory('Book', function ($resource) {
        var Book = $resource('/api/books/:bookId', {bookId: '@id'}, {update: {method: 'PUT'}});
        Book.prototype.isNew = function(){
            return (typeof(this.id) === 'undefined');
        }
        return Book;
    }).
    factory('Carro', function ($resource) {
	    var Carro = $resource('/api/carros/:carroId', {carroId: '@id'}, {update: {method: 'PUT'}});
	    Carro.prototype.isNew = function(){
	        return (typeof(this.id) === 'undefined');
	    }
	    return Carro;
    });

angular.module("sprang", ["sprang.services"]).
    config(function ($routeProvider) {
        $routeProvider
            .when('/books', {templateUrl: '/views/books/list.html', controller: BookListController})
            .when('/books/:bookId', {templateUrl: '/views/books/detail.html', controller: BookDetailController})
	        .when('/carros', {templateUrl: '/views/carros/list.html', controller: CarroListController})
	        .when('/carros/:carroId', {templateUrl: '/views/carros/detail.html', controller: CarroDetailController});
    });

/*angular.module("sprang", ["sprang.services"]).
config(function ($routeProvider) {
    $routeProvider
        .when('/carros', {templateUrl: '/views/carros/list.html', controller: CarroListController})
        .when('/carros/:carroId', {templateUrl: '/views/carros/detail.html', controller: CarroDetailController});
});*/

function BookListController($scope, Book) {
    $scope.books = Book.query();

    $scope.deleteBook = function(book) {
        book.$delete(function() {
            $scope.books.splice($scope.books.indexOf(book),1);
            toastr.success("Deleted");
        });
    }
}

function BookDetailController($scope, $routeParams, $location, Book) {
    var bookId = $routeParams.bookId;

    if (bookId === 'new') {
        $scope.book = new Book();
    } else {
        $scope.book = Book.get({bookId: bookId});
    }

    $scope.save = function () {
        if ($scope.book.isNew()) {
            $scope.book.$save(function (book, headers) {
                toastr.success("Created");
                var location = headers('Location');
                var id = location.substring(location.lastIndexOf('/') + 1);
                $location.path('/books/' + id);
            });
        } else {
            $scope.book.$update(function() {
                toastr.success("Updated");
            });
        }
    };
}


function CarroListController($scope, Carro) {
    $scope.carros = Carro.query();

    $scope.deleteCarro = function(carro) {
        carro.$delete(function() {
            $scope.carros.splice($scope.carros.indexOf(carro),1);
            toastr.success("Deletado");
        });
    }
}

function CarroDetailController($scope, $routeParams, $location, Carro) {
    var carroId = $routeParams.carroId;

    if (carroId === 'new') {
        $scope.carro = new Carro();
    } else {
        $scope.carro = Carro.get({carroId: carroId});
    }

    $scope.salvarCarro = function () {
        if ($scope.carro.isNew()) {
            $scope.carro.$save(function (carro, headers) {
                toastr.success("Criado");
                var location = headers('Location');
                var id = location.substring(location.lastIndexOf('/') + 1);
                $location.path('/carros/' + id);
            });
        } else {
            $scope.book.$update(function() {
                toastr.success("Atualizado");
            });
        }
    };
}

